package com.zyp.service;

import com.zyp.dto.DoctorLoginDto;
import com.zyp.dto.UpdatePasswordDto;
import com.zyp.pojo.Doctor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface DoctorDoctorService {
    Doctor login(DoctorLoginDto doctorLoginDto);

    /**
     * @ description 修改密码
     * @param updatePasswordDto
     * @ return void
     * @ author DELL
     */
    void updatePassword(UpdatePasswordDto updatePasswordDto);

    /**
     * @ description 上传头像
     * @param file
     * @ return java.lang.String
     * @ author DELL
     */
    String updateAvatar(MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
}
