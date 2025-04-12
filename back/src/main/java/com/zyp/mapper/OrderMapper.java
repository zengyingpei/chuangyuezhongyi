package com.zyp.mapper;

import com.zyp.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("insert into `order` (order_no, user_id, address_id, order_time, pay_status, delivery_status, amount) value (#{orderNo},#{userId},#{addressId},#{orderTime},#{payStatus},#{deliveryStatus},#{amount})")
    void insert(Order order);

    @Select("select * from `order` where user_id = #{userId}")
    List<Order> selectAll(Long userId);

    @Select("select * from `order` where pay_status = 0")
    List<Order> selectNoPayOrder();

    @Select("select * from `order` where pay_status =1 and delivery_status = 0")
    List<Order> selectNoDeliveryOrder();

    @Select("select * from `order` where pay_status =1 and delivery_status =1")
    List<Order> selectNotReceiveOrder();

    @Select("select * from `order` where pay_status =1 and delivery_status =2")
    List<Order> selectReceivedOrder();
}
