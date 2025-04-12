package com.zyp.service;

import com.zyp.dto.OrderSubmitDTO;
import com.zyp.pojo.Order;

import java.util.List;

public interface OrderService {
    void submit(OrderSubmitDTO orderSubmitDTO);

    List<Order> selectAll();

    List<Order> selectNoPayOrder();

    List<Order> selectNoDeliveryOrder();

    List<Order> selectNotReceiveOrder();

    List<Order> selectReceivedOrder();
}
