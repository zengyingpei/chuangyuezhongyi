package com.zyp.controller.user;

import com.zyp.dto.OrderSubmitDTO;
import com.zyp.pojo.Order;
import com.zyp.pojo.Result;
import com.zyp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public Result submit(@RequestBody OrderSubmitDTO orderSubmitDTO){
        orderService.submit(orderSubmitDTO);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<List<Order>> selectAll(){
        return Result.success(orderService.selectAll());
    }

    @GetMapping("/nopay")
    public Result<List<Order>> selectNoPayOrder(){
        return Result.success(orderService.selectNoPayOrder());
    }

    @GetMapping("/nodelivery")
    public Result<List<Order>> selectNoDeliveryOrder(){
        return Result.success(orderService.selectNoDeliveryOrder());
    }

    @GetMapping("/noreceive")
    public Result<List<Order>> selectNotReceiveOrder(){
        return Result.success(orderService.selectNotReceiveOrder());
    }

    @GetMapping("/received")
    public Result<List<Order>> selectReceivedOrder(){
        return Result.success(orderService.selectReceivedOrder());
    }
}
