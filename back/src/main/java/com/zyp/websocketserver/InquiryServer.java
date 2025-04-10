package com.zyp.websocketserver;

import com.zyp.dto.ChatContentDto;
import com.zyp.pojo.ChatContent;
import com.zyp.service.ChatService;
import com.zyp.utils.ThreadLocalUtil;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@ServerEndpoint(value = "/ws/inquiry/{sid}")
public class InquiryServer {
    // 存放所有连接到socket服务器的会话对象
    private static Map<String, Session> sessionMap = new HashMap<>();

    @Autowired
    private ChatService chatService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        log.info("客户端: {} 建立连接",sid);
        sessionMap.put(sid, session);
        log.info("当前ws服务器在线人数:{}",sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        log.info("收到来自客户端:{} 的信息:{}",sid,message);

        // 获取信息来自哪边
        String[] sidSplit = sid.split("\\|");
        // 发送者身份
        String status = sidSplit[0];

        // 分割接收到的信息
        String[] s = message.split("\\|");

        // 确定接收方的id
        Long receiverId = Long.parseLong(s[0]);
        // 信息内容
        String msg = s[1];

        Long userId , doctorId;

        // 发给医生
        if(status.equals("user")){
            userId = Long.parseLong(sidSplit[1]);
            doctorId = receiverId;
            sendMessageToDoctor(doctorId, msg);
        }else{
            doctorId = Long.parseLong(sidSplit[1]);
            userId = receiverId;
            sendMessageToUser(userId, msg);
        }
    }

    /**
     * 连接关闭调用的方法
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        log.info("连接断开:{}",sid);
        sessionMap.remove(sid);
    }

    /**
     * 群发(给所有与本ws节点连接的客户端发送消息)
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {  //遍历所有会话
            try {
                //服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @ description send message to doctor
     * @ param doctorId
     * @ param message
     * @ return void
     * @ author DELL
     */
    public void sendMessageToDoctor(Long doctorId, String message) {
        for(Map.Entry<String, Session> en : sessionMap.entrySet()){
            String id = "doctor|"+ String.valueOf(doctorId);
            if(en.getKey().equals(id)){
                try {
                    en.getValue().getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @ description send message to user
     * @ param userId
     * @ param message
     * @ return void
     * @ author DELL
     */
    public void sendMessageToUser(Long userId, String message){
        for(Map.Entry<String, Session> en: sessionMap.entrySet()){
            String id = "user|" + String.valueOf(userId);
            if(en.getKey().equals(id)){
                try {
                    en.getValue().getBasicRemote().sendText(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
