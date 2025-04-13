package com.zyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment implements Serializable {
    private Long id;
    private Long userId;
    private Long doctorId;
    private LocalDate date;
    private Integer timeSlot;
    // 0未付款，1已付款待就诊，2已就诊，3超时取消
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
