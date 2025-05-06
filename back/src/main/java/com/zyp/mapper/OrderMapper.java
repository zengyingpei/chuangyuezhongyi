package com.zyp.mapper;

import com.zyp.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Options(useGeneratedKeys = true , keyProperty = "id" , keyColumn = "id")
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

    @Select("select * from `order` where pay_status = 0 and order_time < #{time}")
    List<Order> selectTimeOutOrder(LocalDateTime time);

    @Update("update `order` set pay_status = #{payStatus}")
    void updatePayStatus(int payStatus);
}
