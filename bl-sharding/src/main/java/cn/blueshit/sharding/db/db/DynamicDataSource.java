package cn.blueshit.sharding.db.db;

import cn.blueshit.sharding.db.DbContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;


/**
 * Created by zhaoheng on 2016/5/20.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbKey();
    }

}
