package com.zyp.mapper;

import com.zyp.pojo.Sickness;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SicknessMapper {

    @Select("select s.* from sickness s left outer join department d on d.id = s.dept_id where d.id=#{deptId}")
    List<Sickness> selectByDeptId(Long deptId);
}
