package com.zyp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCaseDTO implements Serializable {
    private Long linkId;
    private String symptoms;
    private String diagnosis;
    private String treatment;
}
