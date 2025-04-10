package com.zyp.controller.admin;

import com.zyp.dto.AdminLoginDto;
import com.zyp.pojo.Admin;
import com.zyp.pojo.Result;
import com.zyp.properties.JwtProperties;
import com.zyp.service.AdminService;
import com.zyp.utils.JwtUtil;
import com.zyp.vo.AdminLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/admin/admin")
public class AdminLoginController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * @ description 管理员登录
     * @param adminLoginDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/login")
    public Result<AdminLoginVo> login(@RequestBody AdminLoginDto adminLoginDto){
        Admin admin=adminService.login(adminLoginDto);
        log.info("登陆成功{}",admin);

        //为用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //有效载荷中封装 管理员的id
        claims.put("adminId", admin.getId());

        // 生成token
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );

        //封装前端所需要的数据，最重要的是token
        AdminLoginVo adminLoginVo=AdminLoginVo.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .name(admin.getName())
                .token(token)
                .build();


        return Result.success(adminLoginVo);
    }
}
