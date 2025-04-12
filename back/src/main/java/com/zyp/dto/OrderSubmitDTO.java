package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderSubmitDTO implements Serializable {
    private List<Long> ids;
    private Long addressId;
}
