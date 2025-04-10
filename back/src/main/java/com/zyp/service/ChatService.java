package com.zyp.service;

import com.zyp.dto.ChatContentDto;
import com.zyp.pojo.ChatContent;
import com.zyp.pojo.Chatlink;
import com.zyp.vo.ChatContentVo;
import com.zyp.vo.ChatlinkVo;

import java.util.List;

public interface ChatService {
    List<ChatlinkVo> selectAllUserChat();

    List<ChatlinkVo> selectAllDoctorChat();

    /**
     * @ description 获取聊天记录
     * @param chatlinkId
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    List<ChatContentVo> selectByChatLinkId(Long chatlinkId);

    /**
     * @ description 根据聊天的用户id 和 医生id 查询聊天关系id
     * @param userId
     * @param doctorId
     * @ return java.lang.Long
     * @ author DELL
     */
    Long selectIdByUserIdAndDoctorId(Long userId, Long doctorId);

    /**
     * @ description 新增聊天
     * @param content
     * @ return void
     * @ author DELL
     */
    void addMessage(ChatContent content);

    /**
     * @ description 创建新的聊天
     * @param doctorId
     * @ return void
     * @ author DELL
     */
    Long addNew(Long doctorId);
}
