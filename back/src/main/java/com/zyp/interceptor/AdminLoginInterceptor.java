package com.zyp.interceptor;

import com.zyp.properties.JwtProperties;
import com.zyp.utils.JwtUtil;
import com.zyp.utils.ThreadLocalUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class AdminLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * @ description 配置管理端的拦截器
     * @param request
     * @param response
     * @param handler
     * @ return boolean
     * @ author DELL
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前是不是controller方法
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        String token = request.getHeader(jwtProperties.getAdminTokenName());

        try{
            log.info("拦截到请求头中的token,{}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long adminId = Long.valueOf(claims.get("adminId").toString());
            log.info("拦截到的当前管理员的id:{}",adminId);
            //将其存入 threadlocal
            ThreadLocalUtil.set(adminId);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }

    }
}
