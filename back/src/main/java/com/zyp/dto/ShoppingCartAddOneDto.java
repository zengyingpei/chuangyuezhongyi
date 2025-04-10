package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShoppingCartAddOneDto implements Serializable {
    private Long id;
    // 正数是添加，负数是减少
    private Integer num;
}
