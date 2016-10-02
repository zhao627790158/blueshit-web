package cn.blueshit.sharding.paser;

import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.deparser.StatementDeParser;
import org.apache.ibatis.mapping.BoundSql;

/**
 * Created by zhaoheng on 16/10/1.
 */
public class UpdateSqlParser extends AbstractSqlParser<Update> {

    @Override
    protected void onInit() {
        getTables().addAll(getStatement().getTables());
    }

}
