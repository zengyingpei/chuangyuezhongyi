package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AddAppointmentDto implements Serializable {
    //用于定位 时间段表，可用名额可以减一
    private Long slotId;
    // 医生的id
    private Long doctorId;
    // 状态
    private Integer status;
}
