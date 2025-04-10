package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxUserLoginDto implements Serializable {
    //接收小程序发送过来的code
    private String code;
}
