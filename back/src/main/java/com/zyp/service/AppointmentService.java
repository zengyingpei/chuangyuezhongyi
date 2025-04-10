package com.zyp.service;

import com.zyp.dto.AddAppointmentDto;
import com.zyp.vo.AppointmentOfDoctorVo;

import java.util.List;

public interface AppointmentService {
    List<AppointmentOfDoctorVo> getAppointmentByDocId(Long docId);

    void addAppointment(AddAppointmentDto addAppointmentDto);
}
