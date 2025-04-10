package com.zyp.service;

import com.zyp.dto.AdminLoginDto;
import com.zyp.pojo.Admin;

public interface AdminService {
    Admin login(AdminLoginDto adminLoginDto);
}
