package com.zyp.mapper;

import com.zyp.pojo.DocSick;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DocSickMapper {
    /**
     * @ description 医生-疾病中间表插入数据
     * @param docSick
     * @ return void
     * @ author DELL
     */
    @Insert("insert into doc_sick (doc_id, sickness_id) VALUES (#{docId}, #{sicknessId})")
    void insert(DocSick docSick);

    /**
     * @ description 根据医生id删除
     * @param id
     * @ return void
     * @ author DELL
     */
    @Delete("delete from doc_sick where doc_id = #{id}")
    void deleteByDocId(Long id);
}
