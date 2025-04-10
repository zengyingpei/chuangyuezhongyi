package com.zyp.mapper;

import com.zyp.pojo.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MedicineMapper {
    /**
     * @ description 获取所有的治疗药物
     *
     * @ return java.util.List<com.zyp.pojo.Medicine>
     * @ author DELL
     */
    List<Medicine> selectAll1(String name);

    /**
     * @ description 根据分类获取对应的的养生药物
     *
     * @ return java.util.List<com.zyp.pojo.Medicine>
     * @ author DELL
     */
    List<Medicine> selectAll2(Long categoryId,String name);

    /**
     * @ description 根据id查询药物信息
     * @param id
     * @ return com.zyp.pojo.Medicine
     * @ author DELL
     */
    @Select("select * from medicine where id = #{id}")
    Medicine selectById(Long id);
}
