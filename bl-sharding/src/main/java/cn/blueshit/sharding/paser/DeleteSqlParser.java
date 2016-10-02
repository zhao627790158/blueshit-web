package cn.blueshit.sharding.paser;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoheng on 16/10/1.
 */
public class DeleteSqlParser extends AbstractSqlParser<Delete> {

    @Override
    protected void onInit() {
        getTables().add(getStatement().getTable());
    }
}
