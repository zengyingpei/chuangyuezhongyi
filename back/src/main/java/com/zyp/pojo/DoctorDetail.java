package com.zyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDetail implements Serializable {
    private Long id;
    private Long doctorId;
    private String specialization;
    private Integer consultCost;
    private Integer bookingCost;
}
