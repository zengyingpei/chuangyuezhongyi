package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TimeSlotDto implements Serializable {
    // 医生id
    private Long docId;
    // 日期
    private LocalDate date;
    // 时间段（上午/下午）
    private String slot;
    // 总可用人数
    private Integer total;
}
