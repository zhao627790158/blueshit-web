package cn.blueshit.sharding.db;

/**
 * Created by zhaoheng on 2016/5/20.
 */
public class DbContextHolder {

    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<String>();

    private static final ThreadLocal<String> tableIndexHolder = new InheritableThreadLocal<String>();

    public static void setDbKey(String dbKey) {
        contextHolder.set(dbKey);
    }

    public static String getDbKey() {
        return (String) contextHolder.get();
    }

    public static void clearDbKey() {
        contextHolder.remove();
    }

    public static void setTableIndex(String tableIndex) {
        tableIndexHolder.set(tableIndex);
    }

    public static String getTableIndex() {
        return (String) tableIndexHolder.get();
    }

    public static void clearTableIndex() {
        tableIndexHolder.remove();
    }

    public static void clearAll() {
        clearDbKey();
        clearTableIndex();
    }


}
