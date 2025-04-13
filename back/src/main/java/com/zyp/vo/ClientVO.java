package com.zyp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientVO implements Serializable {
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private Integer age;
    private String gender;
    private String idNumber;
}
