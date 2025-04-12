package com.zyp.mapper;

import com.zyp.pojo.Consults;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConsultsMapper {
    @Select("select * from consults")
    List<Consults> selectAll();

    @Select("select * from consults where id =#{id} ")
    Consults selectById(Long id);
}
