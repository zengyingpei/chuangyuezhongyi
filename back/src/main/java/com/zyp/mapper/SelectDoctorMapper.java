package com.zyp.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SelectDoctorMapper {
    /**
     * @ description 根据医生id删除
     * @param id
     * @ return void
     * @ author DELL
     */
    @Delete("delete from select_doctor where doc_id = #{id}")
    void deleteByDocId(Long id);
}
