package cn.blueshit.web.service;

import cn.blueshit.web.po.OrderTable;

/**
 * Created by zhaoheng on 16/10/1.
 */
public interface TestOrderService {

    int testInsertByOrderId(OrderTable orderId, int test);

    OrderTable testSelectByOrderId(OrderTable orderTable);
}
