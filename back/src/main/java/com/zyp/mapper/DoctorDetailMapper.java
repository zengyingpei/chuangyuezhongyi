package com.zyp.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DoctorDetailMapper {

    /**
     * @ description 根据医生id删除
     * @param id
     * @ return void
     * @ author DELL
     */
    @Delete("delete from doctor_detail where doctor_id = #{id}")
    void deleteByDocId(Long id);
}
