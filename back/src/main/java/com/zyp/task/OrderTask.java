package com.zyp.task;

import com.zyp.mapper.MedicineMapper;
import com.zyp.mapper.OrderDetailMapper;
import com.zyp.mapper.OrderMapper;
import com.zyp.pojo.Order;
import com.zyp.pojo.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private MedicineMapper medicineMapper;
    @Transactional
    @Scheduled(cron = "0 * * * * ? ")
    public void autoHandleNoPayOrder() {
        log.info("检查未支付订单");
        // 获得十分钟前的时间
        LocalDateTime time = LocalDateTime.now().plusMinutes(-10);
        List<Order> timeOutOrders = orderMapper.selectTimeOutOrder(time);
        if (timeOutOrders != null && !timeOutOrders.isEmpty()) {
            for (Order order : timeOutOrders) {
                List<OrderDetail> details = orderDetailMapper.selectByOrderId(order.getId());
                for (OrderDetail detail : details) {
                    medicineMapper.add(detail.getMedicineId(), detail.getNumber());
                    orderDetailMapper.logicDelete(detail.getId());
                }
                orderMapper.updatePayStatus(2);
            }
        }
    }
}
