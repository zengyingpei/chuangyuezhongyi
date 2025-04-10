package com.zyp.service;

import com.zyp.pojo.Sickness;

import java.util.List;

public interface SicknessService {
    List<Sickness> selectByDeptId(Long deptId);
}
