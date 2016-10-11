package cn.blueshit.web.test.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhaoheng on 16/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-task.xml")
public class WebTaskTest {


    @Test
    public void test() throws InterruptedException {

        Thread.sleep(30000);

    }


}
