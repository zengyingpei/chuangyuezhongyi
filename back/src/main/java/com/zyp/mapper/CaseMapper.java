package com.zyp.mapper;

import com.zyp.pojo.Case;
import com.zyp.vo.CaseVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CaseMapper {
    /**
     * @ description 根据主键查询
     * @param id
     * @ return com.zyp.pojo.Case
     * @ author DELL
     */
    @Select("select * from `cases` where id = #{id}")
    Case selectById(Long id);

    /**
     * @ description 根据就诊人id查询所有病例
     * @param clientId
     * @ return java.util.List<com.zyp.pojo.Case>
     * @ author DELL
     */
    @Select("select * from `cases` where client_id = #{clientId}")
    List<Case> selectByClientId(Long clientId);

    @Insert("insert into `cases` (client_id, doctor_id, chat_link_id,visit_date, symptoms, diagnosis, treatment) VALUE (#{clientId}, #{doctorId},#{chatLinkId},#{visitDate},#{symptoms},#{diagnosis},#{treatment})")
    void createCase(Case c);

    @Select("select * from cases where chat_link_id = #{chatLinkId}")
    Case selectCaseByChatLinkId(Long chatLinkId);
}
