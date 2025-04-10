package com.zyp.mapper;

import com.zyp.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * @ description 获取所有的养生分类（除了药品）
     *
     * @ return java.util.List<com.zyp.pojo.Category>
     * @ author DELL
     */
    @Select("select * from category where id != 1")
    List<Category> selectAll();
}
