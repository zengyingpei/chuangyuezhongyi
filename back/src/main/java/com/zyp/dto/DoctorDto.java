package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Spliterator;

@Data
public class DoctorDto implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String phone;
    private String avatar;
    private String workplace;
    private String level;
    private String desc;
    private Long deptId;
    private String password;
    private List<Long> sicknessId;
}
