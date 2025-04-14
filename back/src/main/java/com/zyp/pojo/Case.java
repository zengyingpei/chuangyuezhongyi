package com.zyp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Case implements Serializable {
    private Long id;
    private Long clientId;
    private Long doctorId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;
    private String symptoms;
    private String diagnosis;
    private String treatment;
}
