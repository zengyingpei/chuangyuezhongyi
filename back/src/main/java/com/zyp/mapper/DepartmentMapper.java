package com.zyp.mapper;

import com.zyp.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select * from department")
    List<Department> select();
}
