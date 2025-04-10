package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoctorLoginDto  implements Serializable {
    private String name;
    private String password;
}
