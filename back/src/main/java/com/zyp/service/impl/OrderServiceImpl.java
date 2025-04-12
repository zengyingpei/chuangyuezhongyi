package com.zyp.service.impl;

import com.zyp.dto.OrderSubmitDTO;
import com.zyp.mapper.OrderMapper;
import com.zyp.mapper.ShoppingCartMapper;
import com.zyp.pojo.Order;
import com.zyp.service.OrderService;
import com.zyp.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private OrderMapper orderMapper;

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
        Integer payStatus = 1;
        // 未发货
        Integer deliveryStatus = 0;
        BigDecimal amount = new BigDecimal(0);
        List<Long> ids = orderSubmitDTO.getIds();
        for (Long id : ids) {
            BigDecimal price = shoppingCartMapper.selectAmountById(id);
            amount = amount.add(price);
        }

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
