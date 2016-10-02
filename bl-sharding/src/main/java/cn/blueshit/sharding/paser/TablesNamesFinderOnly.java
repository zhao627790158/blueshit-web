package cn.blueshit.sharding.paser;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zhaoheng on 16/10/2.
 */
public class TablesNamesFinderOnly extends TablesNamesFinder {

    private SelectSqlParser selectSqlParser;

    public TablesNamesFinderOnly() {
    }

    public TablesNamesFinderOnly(SelectSqlParser selectSqlParser) {
        this.selectSqlParser = selectSqlParser;
    }

    public void visit(Table tableName) {
        selectSqlParser.getTables().add(tableName);
    }
}
