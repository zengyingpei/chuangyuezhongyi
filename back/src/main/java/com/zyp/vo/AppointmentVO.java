package com.zyp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zyp.pojo.Doctor;
import com.zyp.pojo.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class AppointmentVO implements Serializable {
    private Long id;
    private UserVO user;
    private DoctorVO doctor;
    //private Long doctorId;
    //private String doctorName;
    //private String doctorPhone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String slot;
    private String status;
    private String reason;
}
