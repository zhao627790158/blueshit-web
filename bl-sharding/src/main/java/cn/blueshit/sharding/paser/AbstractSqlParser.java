package cn.blueshit.sharding.paser;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.util.deparser.StatementDeParser;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoheng on 16/10/1.
 * 不要实例化这个类,否则会导致泛型获取失败
 * 只支持一个泛型类型
 */
public abstract class AbstractSqlParser<T> implements SqlParser {

    /**
     * 是否已经初始化
     */
    private boolean inited = false;

    private T statement;

    private final List<Table> tables = new ArrayList<Table>();

    private Class<T> clazz;

    public AbstractSqlParser() {
        //当前对象的直接超类的 Type
        Type genericSuperclass = getClass().getGenericSuperclass();
        // 使用反射技术得到T的真实类型
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
            this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
        }
    }

    public List<Table> getTables() {
        return tables;
    }

    public T getStatement() {
        return statement;
    }

    public void setStatement(T statement) {
        this.statement = statement;
    }

    protected void init() {
        if (inited) {
            return;
        }
        inited = true;
        onInit();
    }

    public String toSQL() {
        StatementDeParser deParser = new StatementDeParser(new StringBuilder());
        Method accept = ReflectionUtils.findMethod(clazz, "accept", StatementVisitor.class);
        ReflectionUtils.invokeMethod(accept, statement, deParser);
        return deParser.getBuffer().toString();
    }

    protected abstract void onInit();
}
