package com.zyp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorVO implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String phone;
    private String avatar;
    private String workplace;
    private String level;
    private String desc;
}
