package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private String name;
    private String phone;
    private String sex;
    private String idNumber;
}
