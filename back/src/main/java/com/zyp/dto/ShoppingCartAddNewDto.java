package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShoppingCartAddNewDto implements Serializable {
    private Long medicineId;
}
