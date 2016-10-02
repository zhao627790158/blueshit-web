package cn.blueshit.sharding.plugin;

import cn.blueshit.sharding.paser.SqlParser;
import cn.blueshit.sharding.paser.SqlParserFactory;
import cn.blueshit.sharding.strategy.ShardStrategy;
import net.sf.jsqlparser.schema.Table;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhaoheng on 16/10/1.
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})})
public class MybatisShardPreparePlugin implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(MybatisShardPreparePlugin.class);

    /**
     * 分库分表规则解析策略
     */
    private final Map<String, ShardStrategy> strategies = new ConcurrentHashMap<String, ShardStrategy>();

    /**
     * 主要作用 用来修改表名
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        Field delegateFiled = ReflectionUtils.findField(handler.getClass(), "delegate");
        delegateFiled.setAccessible(true);
        StatementHandler delegate = (StatementHandler) delegateFiled.get(handler);
        BoundSql boundSql = delegate.getBoundSql();
        //Object parameterObject = boundSql.getParameterObject();
        String originalSql = boundSql.getSql();

        log.info("Sharding Original SQL:{}", originalSql);

        SqlParser sqlParser = SqlParserFactory.getInstance().createParser(originalSql);
        List<Table> tables = sqlParser.getTables();
        if (tables.isEmpty()) {
            return invocation.proceed();
        }
        String logicTableName = tables.get(0).getName();
        ShardStrategy strategy = strategies.get(logicTableName);
        if (strategy == null) {
            return invocation.proceed();
        }
        for (Table t : tables) {
            t.setName(t.getName() + strategy.getFormateTableIndex());
        }

        String targetSQL = sqlParser.toSQL();
        log.info("Sharding SQL:{}", targetSQL);

        //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
        Field sqlFiled = ReflectionUtils.findField(boundSql.getClass(), "sql");
        sqlFiled.setAccessible(true);
        sqlFiled.set(boundSql, targetSQL);
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 以后细化规则
     * 添加读写分离
     * 暂时先装个逼
     *
     * @param properties
     */
    public void setProperties(Properties properties) {
        //xml文件注入的 property
        String configsLocation = properties.getProperty("configsLocation");
        String fileXsd = properties.getProperty("validateXsd");
        if (configsLocation == null || fileXsd == null) {
            throw new IllegalArgumentException(
                    "ShardPlugin[" + getClass().getName() + "] Property[configsLocation] Cannot Empty");
        }

        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream configInputStream = null;
        InputStream validateInputStream = null;
        InputStream xsdInputStream = null;

        try {

            xsdInputStream = classLoader.getResourceAsStream(fileXsd);
            configInputStream = classLoader.getResourceAsStream(configsLocation);
            validateInputStream = classLoader.getResourceAsStream(configsLocation);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = schemaFactory.newSchema(new StreamSource(xsdInputStream));
            Validator validator = schema.newValidator();
            SAXParser parser = factory.newSAXParser();
            SAXReader reader = new SAXReader(parser.getXMLReader());
            validator.validate(new StreamSource(validateInputStream));

            Document document = reader.read(configInputStream);
            Element root = document.getRootElement();
            parseStrategies(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (validateInputStream != null) {
                    validateInputStream.close();
                }
            } catch (IOException e) {
                log.error("invoke ShardPluginInterceptor.setProperties error", e);
            }

            try {
                if (xsdInputStream != null) {
                    xsdInputStream.close();
                }
            } catch (IOException e) {
                log.error("invoke ShardPluginInterceptor.setProperties error", e);
            }

            try {
                if (configInputStream != null) {
                    configInputStream.close();
                }
            } catch (IOException e) {
                log.error("invoke ShardPluginInterceptor.setProperties error", e);
            }
        }
    }

    private void parseStrategies(Element root) throws Exception {
        List<?> strategiesList = root.elements("strategy");
        if (strategiesList != null) {
            for (Object o : strategiesList) {
                Element strategy = (Element) o;
                String logicTable = strategy.attribute("logicTable").getStringValue();
                String strategyClass = strategy.attribute("class").getStringValue();
                Class<?> clazz = Class.forName(strategyClass);
                ShardStrategy shardStrategy = (ShardStrategy) clazz.newInstance();
                if (strategies.containsKey(logicTable)) {
                    throw new IllegalArgumentException("LogicTable[" + logicTable + "] Duplicate");
                }
                strategies.put(logicTable, shardStrategy);
            }
        }
    }
}
