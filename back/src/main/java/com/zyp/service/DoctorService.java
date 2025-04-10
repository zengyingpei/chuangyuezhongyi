package com.zyp.service;

import com.zyp.dto.AdminDoctorPageQueryDto;
import com.zyp.dto.DoctorDto;
import com.zyp.dto.DoctorPageQueryDto;
import com.zyp.pojo.Doctor;
import com.zyp.pojo.PageBean;
import com.zyp.vo.DoctorDeatilVo;

import java.util.List;

public interface DoctorService {
    PageBean<Doctor> selectBySicknessId(DoctorPageQueryDto doctorPageQueryDto);

    List<Doctor> selectSelectedDoctor();

    DoctorDeatilVo selectDoctorDeatil(Long id);

    PageBean<Doctor> pageQuery(AdminDoctorPageQueryDto adminDoctorPageQueryDto);

    void insert(DoctorDto doctorDto);

    void deleteById(Long id);

    void update(DoctorDto doctorDto);
}
