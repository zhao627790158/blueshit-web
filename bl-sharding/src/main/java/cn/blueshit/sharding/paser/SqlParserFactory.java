package cn.blueshit.sharding.paser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.io.StringReader;
import java.sql.SQLException;

/**
 * Created by zhaoheng on 16/10/1.
 */
public class SqlParserFactory {

    private static class SingletonFactory {
        private static final SqlParserFactory instance = new SqlParserFactory();
    }

    public static SqlParserFactory getInstance() {
        return SingletonFactory.instance;
    }

    private final CCJSqlParserManager manager;

    public SqlParserFactory() {
        manager = new CCJSqlParserManager();
    }

    public SqlParser createParser(String originalSql) throws SQLException {
        try {
            Statement statement = manager.parse(new StringReader(originalSql));
            if (statement instanceof Select) {
                SelectSqlParser select = new SelectSqlParser();
                select.setStatement((Select) statement);
                select.init();
                return select;
            } else if (statement instanceof Update) {
                UpdateSqlParser update = new UpdateSqlParser();
                update.setStatement((Update) statement);
                update.init();
                return update;
            } else if (statement instanceof Insert) {
                InsertSqlParser insert = new InsertSqlParser();
                insert.setStatement((Insert) statement);
                insert.init();
                return insert;
            } else if (statement instanceof Delete) {
                DeleteSqlParser delete = new DeleteSqlParser();
                delete.setStatement((Delete) statement);
                delete.init();
                return delete;
            } else {
                throw new SQLException("Unsupported Parser[" + statement.getClass().getName() + "]");
            }
        } catch (JSQLParserException e) {
            throw new SQLException("SQL Parse Failed", e);
        }

    }


}
