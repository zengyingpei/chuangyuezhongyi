package com.zyp.controller.user;

import com.zyp.dto.ChatLinkDto;
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
@RequestMapping("/api/user/chat")
public class ChatUserController {
    @Autowired
    private ChatService chatService;

    /**
     * @ description 用户查询所有的聊天列表
     *
     * @ return com.zyp.pojo.Result<java.util.List<com.zyp.vo.ChatlinkVo>>
     * @ author DELL
     */
    @GetMapping("/list")
    public Result<List<ChatlinkVo>> selectAllUserChat(){
        List<ChatlinkVo> re = chatService.selectAllUserChat();
        return Result.success(re);
    }

    /**
     * @ description 用户获取与某个医生的聊天记录(根据列表id)
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
     * @ description 创建新的聊天
     * @param chatLinkDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/new")
    public Result addNew(@RequestBody ChatLinkDto chatLinkDto){
        Long chatLinkId = chatService.addNew(chatLinkDto);
        return Result.success(chatLinkId);
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
