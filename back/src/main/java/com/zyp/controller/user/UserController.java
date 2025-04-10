package com.zyp.controller.user;

import com.zyp.dto.UserDto;
import com.zyp.dto.WxUserLoginDto;
import com.zyp.mapper.UserMapper;
import com.zyp.pojo.Result;
import com.zyp.pojo.User;
import com.zyp.properties.JwtProperties;
import com.zyp.service.UserService;
import com.zyp.utils.JwtUtil;
import com.zyp.utils.Md5Util;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.WxUserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
//import org.apiguardian.api.API;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/user/user")
@Slf4j
@Api(tags = "wx用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation("微信用户登录")
    @PostMapping("/login")
    public Result<WxUserLoginVo> login(@RequestBody WxUserLoginDto wxUserLoginDto){
        log.info("用户的code " + wxUserLoginDto.getCode());
        //业务层login逻辑
        User user=userService.login(wxUserLoginDto);

        //生成jwt
        Map<String, Object> claims=new HashMap<>();
        claims.put("userId", user.getId());     //获取用户的id
        String token=JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(),claims);

        //封装返回给前端的数据
        WxUserLoginVo wxUserLoginVo=WxUserLoginVo.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(wxUserLoginVo);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("update")
    public Result update(@RequestBody UserDto userDto){
        userService.update(userDto);
        return Result.success();
    }
}
