package com.zyp.controller.doctor;

import com.zyp.dto.ChatContentDto;
import com.zyp.pojo.ChatContent;
import com.zyp.pojo.Result;
import com.zyp.service.ChatService;
import com.zyp.vo.ChatContentVo;
import com.zyp.vo.ChatlinkVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/doctor/chat")
public class ChatDoctorController {
    @Autowired
    private ChatService chatService;

    /**
     * @ description 医生查询所有的聊天列表
     *
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @GetMapping("/list")
    public Result<List<ChatlinkVo>> selectAllDoctorChat(){
        List<ChatlinkVo> re = chatService.selectAllDoctorChat();
        return Result.success(re);
    }

    /**
     * @ description 获取聊天记录(根据列表id)
     * @param chatlinkId
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @GetMapping("/one")
    public Result selectByChatLinkId(@RequestParam Long chatlinkId){
        List<ChatContentVo> re = chatService.selectByChatLinkId(chatlinkId);
        return Result.success(re);
    }

    /**
     * @ description 新增聊天记录
     * @param content
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/add")
    public Result addMessage(@RequestBody ChatContent content){
        chatService.addMessage(content);
        return Result.success();
    }
}
