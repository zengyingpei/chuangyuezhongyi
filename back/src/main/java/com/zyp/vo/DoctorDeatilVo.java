package com.zyp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDeatilVo implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String phone;
    private String avatar;
    private String workplace;
    private String level;
    private String desc;
    private String card;
    private String specialization;
    private BigDecimal score;               //需要动态计算平均评分
    private List<String> department;        //另外查询属于什么科室
    private Integer consultCost;            //图文咨询的费用
    private Integer bookingCost;            //预约挂号的费用
}
