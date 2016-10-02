package cn.blueshit.testweb.service;

import cn.blueshit.testweb.po.OrderTable;

/**
 * Created by zhaoheng on 16/10/1.
 */
public interface TestOrderService {

    int testInsertByOrderId(OrderTable orderId, int test);

    OrderTable testSelectByOrderId(OrderTable orderTable);
}
