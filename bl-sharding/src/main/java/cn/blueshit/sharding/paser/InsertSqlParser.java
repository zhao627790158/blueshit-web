package cn.blueshit.sharding.paser;

import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

/**
 * Created by zhaoheng on 16/10/1.
 */
public class InsertSqlParser extends AbstractSqlParser<Insert> {

    @Override
    protected void onInit() {
        getTables().add(getStatement().getTable());
    }
}
