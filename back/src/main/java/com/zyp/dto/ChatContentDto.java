package com.zyp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatContentDto implements Serializable {
    private Long linkId;
    private Long receiver;
    private String content;
}
