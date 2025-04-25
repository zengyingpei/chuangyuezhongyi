package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatLinkDto implements Serializable {
    private Long doctorId;
    private Long clientId;
}
