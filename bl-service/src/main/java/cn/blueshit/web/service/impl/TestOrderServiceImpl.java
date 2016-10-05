package cn.blueshit.web.service.impl;

import cn.blueshit.sharding.db.annotation.DoRoute;
import cn.blueshit.web.dao.OrderTableDao;
import cn.blueshit.web.po.OrderTable;
import cn.blueshit.web.service.TestOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaoheng on 16/10/1.
 */
public class TestOrderServiceImpl implements TestOrderService {

    private final static Logger log = LoggerFactory.getLogger(TestOrderServiceImpl.class);

    private OrderTableDao orderTableDao;

    @DoRoute(routeFiled = "orderId")
    public int testInsertByOrderId(OrderTable orderTable, int testId) {
        int i = orderTableDao.insertByOrderId(orderTable.getOrderId());
        log.info("--------:{}", i);
        return i;

    }

    @DoRoute(routeFiled = "orderId")
    public OrderTable testSelectByOrderId(OrderTable orderTable) {
        OrderTable orderTable1 = orderTableDao.selectByOrderId(orderTable.getOrderId());
        log.info("select :{}", orderTable1);

        return orderTable1;
    }

    public OrderTableDao getOrderTableDao() {
        return orderTableDao;
    }

    public void setOrderTableDao(OrderTableDao orderTableDao) {
        this.orderTableDao = orderTableDao;
    }
}
