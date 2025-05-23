package com.zyp.service;

import com.zyp.dto.AddAppointmentDto;
import com.zyp.vo.AppointmentOfDoctorVo;
import com.zyp.vo.AppointmentVO;

import java.util.List;

public interface AppointmentService {
    List<AppointmentOfDoctorVo> getAppointmentByDocId(Long docId);

    void addAppointment(AddAppointmentDto addAppointmentDto);

    List<AppointmentVO> selectAllNoFinished();

    List<AppointmentVO> selectAllFinished();

    void cancelAppointment(long id);

    List<AppointmentVO> selectAllByDoctorId();

    void finish(long id);

    void refuse(long id, String reason);
}
