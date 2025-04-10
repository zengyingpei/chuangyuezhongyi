package com.zyp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorLoginVo implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private String avatar;
    private String token;
}
