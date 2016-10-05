package cn.blueshit.web.test;


import cn.blueshit.sharding.db.RouteUtils;
import cn.blueshit.sharding.db.hash.MurmurHash;
import org.junit.Test;

import java.text.DecimalFormat;

/**
 * Created by zhaoheng on 16/10/5.
 */
public class TestTableIndex {


    @Test
    public void test1() throws Exception {
        long test = MurmurHash.hash("test");
        System.out.println(test);


    }

    @Test
    public void test2() {
        String test = "avia";
        int hashCodeBase64 = RouteUtils.getHashCodeBase64(test);
        System.out.println(hashCodeBase64);

        int hashCode = 22231;
        int dbNumber = 10;
        int tableNumber = 5;
        int mode = dbNumber * tableNumber;//取余 绝对比 乘积要小
        int dbIndex = hashCode % mode / tableNumber;
        int tableIndex = hashCode % mode / dbNumber;
        System.out.println(dbIndex);

        DecimalFormat df = new DecimalFormat();
        String style = "_0000";//在格式后添加诸如单位等字符
        df.applyPattern(style);
        String test11 = df.format(tableIndex);

        System.out.println(test11);//_004
    }


}
