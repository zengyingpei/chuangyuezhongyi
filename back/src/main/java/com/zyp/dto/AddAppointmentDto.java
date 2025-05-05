package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AddAppointmentDto implements Serializable {
    //用于定位 接诊排班表
    private Long scheduleId;
    // 医生的id
    private Long doctorId;
    // 状态
    private Integer status;
}
