package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordDto implements Serializable {
    private String password;
    private String confirmPassword;
}
