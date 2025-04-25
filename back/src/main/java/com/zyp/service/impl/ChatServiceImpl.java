package com.zyp.service.impl;

import com.zyp.dto.ChatContentDto;
import com.zyp.dto.ChatLinkDto;
import com.zyp.mapper.ChatMapper;
import com.zyp.mapper.DoctorMapper;
import com.zyp.mapper.UserMapper;
import com.zyp.pojo.ChatContent;
import com.zyp.pojo.Chatlink;
import com.zyp.pojo.Doctor;
import com.zyp.service.ChatService;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.ChatContentVo;
import com.zyp.vo.ChatlinkVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * @ description 用户查询所有的聊天列表
     *
     * @ return java.util.List<com.zyp.vo.ChatlinkVo>
     * @ author DELL
     */
    @Transactional
    public List<ChatlinkVo> selectAllUserChat() {
        Long userId = ThreadLocalUtil.get();
        List<Chatlink> list = chatMapper.selectAllUserChat(userId);
        List<ChatlinkVo> ans = new ArrayList<>();
        for (Chatlink chatlink : list) {
            ChatlinkVo vo = new ChatlinkVo();
            BeanUtils.copyProperties(chatlink, vo);
            Doctor doctor = doctorMapper.selectById(chatlink.getDoctorId());
            vo.setDoctorName(doctor.getName());
            vo.setDoctorAvatar(doctor.getAvatar());
            ans.add(vo);
        }

        return ans;
    }

    /**
     * @ description 医生查询所有的聊天列表
     *
     * @ return java.util.List<com.zyp.pojo.Chatlink>
     * @ author DELL
     */
    @Transactional
    public List<ChatlinkVo> selectAllDoctorChat() {
        Long doctorId = ThreadLocalUtil.get();
        List<Chatlink> list = chatMapper.selectAllDoctorChat(doctorId);
        List<ChatlinkVo> ans = new ArrayList<>();
        for (Chatlink chatlink : list) {
            ChatlinkVo vo =new ChatlinkVo();
            BeanUtils.copyProperties(chatlink, vo);
            String userName = userMapper.selectNameById(chatlink.getUserId());
            vo.setUsername(userName);
            ans.add(vo);
        }

        return ans;
    }

    /**
     * @ description 获取聊天记录
     * @param chatlinkId
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    public List<ChatContentVo> selectByChatLinkId(Long chatlinkId) {
        List<ChatContent> contents = chatMapper.selectByChatLinkId(chatlinkId);

        List<ChatContentVo> ans = new ArrayList<>();

        for (ChatContent content : contents) {
            ChatContentVo chatContentVo = ChatContentVo.builder()
                    .receiver(content.getReceiver())
                    .sender(content.getSender())
                    .content(content.getContent())
                    .time(content.getTime())
                    .state(content.getState())
                    .build();

            ans.add(chatContentVo);
        }

        return ans;
    }

    /**
     * @ description 根据聊天的用户id 和 医生id 查询聊天关系id
     * @param userId
     * @param doctorId
     * @ return java.lang.Long
     * @ author DELL
     */
    public Long selectIdByUserIdAndDoctorId(Long userId, Long doctorId) {
        return chatMapper.selectIdByUserIdAndDoctorId(userId, doctorId);
    }

    /**
     * @ description 新增聊天记录
     * @param content
     * @ return void
     * @ author DELL
     */
    public void addMessage(ChatContent content) {
//        ChatContent chatContent = new ChatContent();
//        BeanUtils.copyProperties(chatContentDto,chatContent);
        Long sender = ThreadLocalUtil.get();
        content.setSender(sender);
        content.setTime(LocalDateTime.now());
        content.setState(0);
        chatMapper.addMessage(content);
    }

    /**
     * @ description 创建新的聊天
     * @param chatLinkDto
     * @ return void
     * @ author DELL
     */
    public Long addNew(ChatLinkDto chatLinkDto) {

        long doctorId = chatLinkDto.getDoctorId();
        long clientId = chatLinkDto.getClientId();
        Long chatLinkId = chatMapper.selectIdByUserIdAndDoctorId(ThreadLocalUtil.get(), doctorId);
        if(chatLinkId==null){
            Chatlink chatlink = Chatlink.builder()
                    .userId(ThreadLocalUtil.get())
                    .clientId(clientId)
                    .doctorId(doctorId)
                    .createTime(LocalDateTime.now())
                    .build();

            chatMapper.addNew(chatlink);
            return chatlink.getId();
        }else{
            return chatLinkId;
        }

    }
}


