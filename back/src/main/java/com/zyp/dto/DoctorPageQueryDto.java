package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoctorPageQueryDto implements Serializable {
    private int page;           //页码
    private int pageSize;       //每页记录数
    private Long sicknessId;    //哪个疾病（id）的医生
}
