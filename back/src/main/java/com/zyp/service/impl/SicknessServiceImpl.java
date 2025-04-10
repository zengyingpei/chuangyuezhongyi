package com.zyp.service.impl;

import com.zyp.mapper.SicknessMapper;
import com.zyp.pojo.Sickness;
import com.zyp.service.SicknessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SicknessServiceImpl implements SicknessService {
    @Autowired
    private SicknessMapper sicknessMapper;
    @Override
    public List<Sickness> selectByDeptId(Long deptId) {
        List<Sickness> list=sicknessMapper.selectByDeptId(deptId);
        return list;
    }
}
