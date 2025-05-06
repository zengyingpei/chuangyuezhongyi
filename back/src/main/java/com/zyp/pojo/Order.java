package com.zyp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long addressId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime orderTime;
    // 0未支付 , 1已支付, 2超时未支付取消
    private Integer payStatus;
    // 0未发货 ， 1已发货 但未收货，2已收货
    private Integer deliveryStatus;
    // 收款金额
    private BigDecimal amount;
}
