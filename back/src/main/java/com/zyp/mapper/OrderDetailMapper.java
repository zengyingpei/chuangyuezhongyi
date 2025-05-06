package com.zyp.mapper;

import com.zyp.pojo.OrderDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    @Insert("insert into order_detail(order_id, medicine_id, number, price) VALUE (#{orderId} , #{medicineId} , #{number} , #{price})")
    void insertOrderTerm(OrderDetail orderDetail);

    @Select("select * from `order_detail` where order_id = #{orderId}")
    List<OrderDetail> selectByOrderId(long orderId);

    @Update("update order_detail set logic_delete = 1 where id = #{id}")
    void logicDelete(long id);
}
