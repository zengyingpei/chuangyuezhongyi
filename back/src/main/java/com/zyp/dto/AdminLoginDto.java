package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginDto implements Serializable {
    private String username;
    private String password;
    private String phone;
}
