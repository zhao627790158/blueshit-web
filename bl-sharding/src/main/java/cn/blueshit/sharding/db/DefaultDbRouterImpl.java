package cn.blueshit.sharding.db;

import cn.blueshit.sharding.db.bean.DbRuleSet;
import cn.blueshit.sharding.db.hash.MurmurHash;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaoheng on 2016/5/20.
 * 路由规则计算
 */
public class DefaultDbRouterImpl implements DbRouter {

    private static final Logger log = LoggerFactory.getLogger(DefaultDbRouterImpl.class);

    //数据源列表
    private List<DbRuleSet> dbRuleSetList;

    public String doRoute(String fieldId) {
        //获取字符串的hashcode
        int routeFieldInt = RouteUtils.getResourceCode(fieldId);
        String dbKey = getDbKeyAndSetThreadLocalIndex(dbRuleSetList, routeFieldInt);
        return dbKey;
    }


    public String doRouteByPayId(String resourceCode) {
        //根据业务的扩展
        return null;
    }

    private String getDbKeyAndSetThreadLocalIndex(List<DbRuleSet> ruleSets, int routeFieldInt) {
        // 路由配置规则
        DbRuleSet dbRuleSet = null;
        if (ruleSets == null || ruleSets.size() <= 0) {
            throw new IllegalArgumentException("dbRuleSetList不能为空");
        }
        String dbKey = null;
        //根据配置文件中的 路由规则来生产key
        for (DbRuleSet dbRuleSet1 : ruleSets) {
            if (dbRuleSet1.getRuleType() == DbRuleSet.RULE_TYPE_STR) {
                dbRuleSet = dbRuleSet1;
                if (dbRuleSet.getDbKeyArray() != null && dbRuleSet.getDbNumber() != 0) {
                    long dbIndex = 0;
                    long tbIndex = 0;
                    //默认按照分库进行计算
                    long mode = dbRuleSet.getDbNumber();
                    //如果是按照分库分表的话，计算
                    if (dbRuleSet1.getRouteType() == DbRuleSet.ROUTE_TYPE_DBANDTABLE && dbRuleSet1.getTableNumber() != 0) {
                        mode = dbRuleSet.getDbNumber() * dbRuleSet1.getTableNumber();
                        dbIndex = routeFieldInt % mode / dbRuleSet1.getTableNumber();
                        tbIndex = routeFieldInt % dbRuleSet1.getTableNumber();
                        String tableIndex = getFormateTableIndex(dbRuleSet1.getTableIndexStyle(), tbIndex);
                        DbContextHolder.setTableIndex(tableIndex);
                        log.info("tableIndex:{}", tableIndex);
                    } else if (dbRuleSet1.getRouteType() == DbRuleSet.ROUTE_TYPE_DB) {
                        mode = dbRuleSet.getDbNumber();
                        dbIndex = routeFieldInt % mode;
                    } else if (dbRuleSet1.getRouteType() == DbRuleSet.ROUTE_TYPE_TABLE) {
                        tbIndex = routeFieldInt % dbRuleSet1.getTableNumber();
                        String tableIndex = getFormateTableIndex(dbRuleSet1.getTableIndexStyle(), tbIndex);
                        DbContextHolder.setTableIndex(tableIndex);
                        log.info("tableIndex:{}", tableIndex);
                    }
                    dbKey = dbRuleSet.getDbKeyArray().get(Long.valueOf(dbIndex).intValue());
                    log.info("dbkey{}", dbKey);
                    DbContextHolder.setDbKey(dbKey);
                }
                break;
            }
        }
        return dbKey;
    }

    private static String getFormateTableIndex(String style, long tbIndex) {
        String tableIndex = null;
        DecimalFormat df = new DecimalFormat();
        if (StringUtils.isEmpty(style)) {
            style = "_0000";//在格式后添加诸如单位等字符
        }
        df.applyPattern(style);
        tableIndex = df.format(tbIndex);
        return tableIndex;
    }

    public List<DbRuleSet> getDbRuleSetList() {
        return dbRuleSetList;
    }

    public void setDbRuleSetList(List<DbRuleSet> dbRuleSetList) {
        this.dbRuleSetList = dbRuleSetList;
    }

    private static void main(String[] args) {
        int id = 232311;
        int dbNumber = 10;
        int tableNumber = 5;
        int mode = dbNumber * tableNumber;//取余 绝对比 乘积要小
        int dbIndex = id % mode / tableNumber;
        System.out.println(dbIndex);

    }
}
