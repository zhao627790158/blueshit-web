package cn.blueshit.sharding.strategy;

import cn.blueshit.sharding.db.DbContextHolder;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

/**
 * Created by zhaoheng on 16/10/1.
 */
public class SimpleShardStrategy implements ShardStrategy {


    public String getFormateTableIndex() {
        return DbContextHolder.getTableIndex();
    }
}
