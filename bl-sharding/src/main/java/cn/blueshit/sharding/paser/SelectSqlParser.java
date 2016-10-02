package cn.blueshit.sharding.paser;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

import java.util.Iterator;

/**
 * Created by zhaoheng on 16/10/1.
 */
public class SelectSqlParser extends AbstractSqlParser<Select> {
    @Override
    protected void onInit() {
        TablesNamesFinderOnly tablesNamesFinderOnly = new TablesNamesFinderOnly(this);
        tablesNamesFinderOnly.getTableList(this.getStatement());
    }

}

