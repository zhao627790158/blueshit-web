package cn.blueshit.web.test.task;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaoheng on 16/10/11.
 */
public class ExampleTask {

    private int pageNum = 1;

    private int pageSize = 20;


    public void execute() {
        System.out.println("定时任务:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "处理页:" + pageNum + ",每页数量:" + pageSize);

    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
