package com.zyp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zyp.dto.UserDto;
import com.zyp.dto.WxUserLoginDto;
import com.zyp.exception.LoginFailedException;
import com.zyp.mapper.UserMapper;
import com.zyp.pojo.User;
import com.zyp.properties.WeChatProperties;
import com.zyp.service.UserService;
import com.zyp.utils.HttpClientUtil;
import com.zyp.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String JSCODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatProperties weChatProperties;

    private String getOpenid(String wxCode){
        //封装用于请求的get参数
        Map<String, String> map=new HashMap();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", wxCode);
        map.put("grant_type", "authorization_code");

        //调用http工具类，向微信的接口发送请求，携带数据
        String json_result= HttpClientUtil.doGet(JSCODE2SESSION_URL, map);
        log.info(json_result);

        //解析json数据
        JSONObject jsonObject=JSON.parseObject(json_result);
        String openid=jsonObject.getString("openid");
        log.info("用户的openid "+ openid);

        return openid;
    }


    @Override
    public User login(WxUserLoginDto wxUserLoginDto) {
        String openid = getOpenid(wxUserLoginDto.getCode());

        if(openid==null) {
            throw new LoginFailedException("登陆失败");
        }

        User user=userMapper.selectByOpenid(openid);

        if(user==null){     //说明当前是一个新用户
            user=User.builder()
                    .createTime(LocalDateTime.now())
                    .openid(openid)
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    @Override
    public void update(UserDto userDto) {
        User user=new User();
        BeanUtils.copyProperties(userDto, user);
        Long id=ThreadLocalUtil.get();
        user.setId(id);
        userMapper.update(user);
    }
}
