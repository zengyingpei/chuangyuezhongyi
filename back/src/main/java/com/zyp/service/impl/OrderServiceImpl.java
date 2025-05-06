package com.zyp.service.impl;

import com.zyp.dto.OrderSubmitDTO;
import com.zyp.mapper.MedicineMapper;
import com.zyp.mapper.OrderDetailMapper;
import com.zyp.mapper.OrderMapper;
import com.zyp.mapper.ShoppingCartMapper;
import com.zyp.pojo.Order;
import com.zyp.pojo.OrderDetail;
import com.zyp.pojo.ShoppingCart;
import com.zyp.service.OrderService;
import com.zyp.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private MedicineMapper medicineMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * @ description 下单
     * @param orderSubmitDTO
     * @ return void
     * @ author DELL
     */
    @Transactional
    public void submit(OrderSubmitDTO orderSubmitDTO) {
        Long userId = ThreadLocalUtil.get();
        String orderNo = generateOrderNo();
        Long addressId = orderSubmitDTO.getAddressId();
        LocalDateTime orderTime = LocalDateTime.now();
        // 已支付
        Integer payStatus = 0;
        // 未发货
        Integer deliveryStatus = 0;

        BigDecimal amount = new BigDecimal(0);
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        List<Long> ids = orderSubmitDTO.getIds();
        for (Long shoppingCartId : ids) {
            ShoppingCart shoppingCart = shoppingCartMapper.selectById(shoppingCartId);
            shoppingCartList.add(shoppingCart);

            // 累加金额
            BigDecimal price = shoppingCart.getPrice();
            amount = amount.add(price);
        }

        // 订单表新增记录
        Order order = Order.builder()
                .orderNo(orderNo)
                .addressId(addressId)
                .userId(userId)
                .orderTime(orderTime)
                .payStatus(payStatus)
                .deliveryStatus(deliveryStatus)
                .amount(amount)
                .build();
        orderMapper.insert(order);

        // 订单项详细信息表
        long orderId = order.getId();

        for (ShoppingCart shoppingCart : shoppingCartList) {
            // 购物车中对应的药品库存减少
            medicineMapper.subtract(shoppingCart.getMedicineId() , shoppingCart.getNumber() );

            OrderDetail orderDetail = OrderDetail.builder()
                    .orderId(orderId)
                    .medicineId(shoppingCart.getMedicineId())
                    .number(shoppingCart.getNumber())
                    .price(shoppingCart.getPrice())
                    .build();

            orderDetailMapper.insertOrderTerm(orderDetail);
        }

        // 清除购物车
        for (Long id : ids) {
            shoppingCartMapper.deleteOne(id);
        }
    }

    public List<Order> selectAll(){
        Long userId = ThreadLocalUtil.get();
        return orderMapper.selectAll(userId);
    }

    @Override
    public List<Order> selectNoPayOrder() {
        return orderMapper.selectNoPayOrder();
    }

    @Override
    public List<Order> selectNoDeliveryOrder() {
        return orderMapper.selectNoDeliveryOrder();
    }

    @Override
    public List<Order> selectNotReceiveOrder() {
        return orderMapper.selectNotReceiveOrder();
    }

    @Override
    public List<Order> selectReceivedOrder() {
        return orderMapper.selectReceivedOrder();
    }

    /**
     * @ description 生成一个12位的订单号
     * @ return java.lang.String
     * @ author DELL
     */
    private String generateOrderNo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
        String timePart = LocalDateTime.now().format(formatter); // 8位
        int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999); // 4位随机数
        return timePart + String.format("%04d", randomNum); // 8+4=12位
    }

}
