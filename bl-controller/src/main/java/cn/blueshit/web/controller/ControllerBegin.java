package cn.blueshit.web.controller;

import cn.blueshit.web.po.OrderTable;
import cn.blueshit.web.service.TestOrderService;
import cn.blueshit.web.utils.Persion;
import com.google.common.collect.Lists;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * Created by zhaoheng on 16/8/31.
 */
@Controller
public class ControllerBegin {


    @Resource(name = "testOrderService")
    private TestOrderService testOrderService;


    @RequestMapping("/index")
    public String test11() {
        return "index";

    }


    @RequestMapping(value = "/test")
    @ResponseBody
    public Object testRestful(HttpServletRequest request, String test, Persion persion) throws Exception {
        System.out.println(request);
        System.out.println(persion.getName());
       /* try {
            int i = 1 / 0;
            System.out.println("continue");
        } catch (Exception e) {
            System.out.println("execption");
            throw e;
        } finally {
            throw new RuntimeException("run execption");
        }*/
        return Lists.newArrayList(1, 2, 3, 4, 5, 6);

    }

    @RequestMapping(value = "/testInsert")
    @ResponseBody
    public Object testRestfulInsert() throws Exception {

        OrderTable orderTable = new OrderTable();
        orderTable.setOrderId(new Random().nextLong());

        testOrderService.testInsertByOrderId(orderTable, 111);
        return "xxx";

    }

    @RequestMapping(value = "/testSelect")
    @ResponseBody
    public Object testRestfulSelect(String orderId) throws Exception {

        OrderTable orderTable = new OrderTable();
        orderTable.setOrderId(NumberUtils.toLong(orderId));
        return testOrderService.testSelectByOrderId(orderTable);

    }
}
