package com.zyp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyp.pojo.Client;
import com.zyp.pojo.Doctor;
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
public class CaseVO implements Serializable {
    private Long id;
//    private Long clientId;
    private Client client;
//    private Long doctorId;
    private Doctor doctor;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;
    private String symptoms;
    private String diagnosis;
    private String treatment;
}
