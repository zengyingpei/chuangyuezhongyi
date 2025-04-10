package com.zyp.service.impl;

import com.zyp.mapper.DepartmentMapper;
import com.zyp.pojo.Department;
import com.zyp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List<Department> select() {
        List<Department> list=departmentMapper.select();
        return list;
    }
}
