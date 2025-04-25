package com.zyp.mapper;

import com.zyp.pojo.Case;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CaseMapper {
    /**
     * @ description 根据主键查询
     * @param id
     * @ return com.zyp.pojo.Case
     * @ author DELL
     */
    @Select("select * from `case` where id = #{id}")
    Case selectById(Long id);

    /**
     * @ description 根据就诊人id查询所有病例
     * @param clientId
     * @ return java.util.List<com.zyp.pojo.Case>
     * @ author DELL
     */
    @Select("select * from `case` where client_id = #{clientId}")
    List<Case> selectByClientId(Long clientId);

    @Insert("insert into `case` (client_id, doctor_id, visit_date, symptoms, diagnosis, treatment) VALUE (#{clientId}, #{doctorId},#{visitDate},#{symptoms},#{diagnosis},#{treatment})")
    void createCase(Case c);
}
