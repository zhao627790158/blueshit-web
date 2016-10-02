package cn.blueshit.sharding.paser;

import net.sf.jsqlparser.schema.Table;

import java.util.List;

/**
 * Created by zhaoheng on 16/10/1.
 * sql解析
 */
public interface SqlParser {

    /**
     * 获取表明
     *
     * @return
     */
    public List<Table> getTables();

    /**
     * 获取执行的sql语句
     *
     * @return
     */
    public String toSQL();
}
