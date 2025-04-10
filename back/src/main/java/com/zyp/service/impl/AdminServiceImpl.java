package com.zyp.service.impl;

import com.zyp.dto.AdminLoginDto;
import com.zyp.exception.AccountNotFoundException;
import com.zyp.exception.PasswordErrorException;
import com.zyp.mapper.AdminMapper;
import com.zyp.pojo.Admin;
import com.zyp.service.AdminService;
import com.zyp.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(AdminLoginDto adminLoginDto) {
        String username = adminLoginDto.getUsername();
        String password = adminLoginDto.getPassword();
        Admin admin=adminMapper.selectByUsername(username);

        //账号不存在
        if(admin==null){
            throw new AccountNotFoundException("帐号不存在");
        }

        //对比密码
        password = Md5Util.getMD5String(password);
        if(!password.equals(admin.getPassword())){
            throw new PasswordErrorException("密码错误");
        }
        return admin;
    }
}
