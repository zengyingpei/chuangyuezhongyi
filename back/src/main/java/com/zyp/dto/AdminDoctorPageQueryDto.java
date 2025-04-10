package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminDoctorPageQueryDto implements Serializable {
    private int page;
    private int pageSize;
    private String name;
}
