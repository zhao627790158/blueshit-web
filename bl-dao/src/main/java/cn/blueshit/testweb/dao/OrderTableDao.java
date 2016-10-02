package cn.blueshit.testweb.dao;


import cn.blueshit.testweb.po.OrderTable;
import org.apache.ibatis.annotations.Param;

public interface OrderTableDao {


    int insertByOrderId(@Param("orderId") Long orderId);


    OrderTable selectByOrderId(@Param("orderId") Long orderId);


}
