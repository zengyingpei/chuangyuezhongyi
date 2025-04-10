package com.zyp.mapper;

import com.zyp.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);
}
