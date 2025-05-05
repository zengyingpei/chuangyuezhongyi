package com.zyp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentOfDoctorVo implements Serializable {
    //时间段表的id
    private Long id;
    //精确到年月日
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    //表示时间段（上午/下午）
    private String slot;
    //表示剩余可用人数
    private Integer surplus;
    //表示总人数 (弃用)
    private Integer total;
}
