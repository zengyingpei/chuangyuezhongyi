package com.zyp.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ConsultsVO implements Serializable {
    private Long id;
    private String title;
    private String content;
    private String image;
    private String from;
}
