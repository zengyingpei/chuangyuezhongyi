package com.zyp.mapper;

import com.zyp.pojo.ChatContent;
import com.zyp.pojo.Chatlink;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMapper {

    /**
     * @ description 用户查询聊天列表
     * @param userId
     * @ return java.util.List<com.zyp.pojo.Chatlink>
     * @ author DELL
     */
    @Select("select * from chatlink where user_id = #{userId}")
    List<Chatlink> selectAllUserChat(Long userId);

    /**
     * @ description 医生查询聊天列表
     * @param doctorId
     * @ return java.util.List<com.zyp.pojo.Chatlink>
     * @ author DELL
     */
    @Select("select  * from chatlink where doctor_id = #{doctorId}")
    List<Chatlink> selectAllDoctorChat(Long doctorId);

    /**
     * @ description 根据列表id查询聊天记录
     * @param chatlinkId
     * @ return java.util.List<com.zyp.pojo.ChatContent>
     * @ author DELL
     */
    @Select("select * from chat_content where link_id = #{chatlinkId}")
    List<ChatContent> selectByChatLinkId(Long chatlinkId);

    @Select("select id from chatlink where user_id = #{userId} and doctor_id = #{doctorId}")
    Long selectIdByUserIdAndDoctorId(Long userId, Long doctorId);


    /**
     * @ description 新增聊天信息
     * @param chatContent
     * @ return void
     * @ author DELL
     */
    @Insert("insert into chat_content (link_id, receiver, sender, content, time, state) values " +
            "(#{linkId}, #{receiver}, #{sender}, #{content}, #{time}, #{state})")
    void addMessage(ChatContent chatContent);

    /**
     * @ description 创建新的聊天
     * @param chatlink
     * @ return void
     * @ author DELL
     */
    @Insert("insert into chatlink (user_id, client_id, doctor_id, create_time) values " +
            "(#{userId} ,#{clientId}, #{doctorId} , #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addNew(Chatlink chatlink);

    @Select("select * from chatlink where id = #{linkId}")
    Chatlink selectByLinkId(Long linkId);
}
