package com.zyp.service.impl;

import com.zyp.dto.DoctorLoginDto;
import com.zyp.dto.UpdatePasswordDto;
import com.zyp.exception.AccountNotFoundException;
import com.zyp.exception.NotFoundProperties;
import com.zyp.exception.PasswordErrorException;
import com.zyp.mapper.DoctorMapper;
import com.zyp.pojo.Doctor;
import com.zyp.properties.MinioProperties;
import com.zyp.service.DoctorDoctorService;
import com.zyp.utils.Md5Util;
import com.zyp.utils.MinioUtil;
import com.zyp.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class DoctorDoctorServiceImpl implements DoctorDoctorService {
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    private MinioProperties minioProperties;

    /**
     * @ description 医生端口登录
     * @param doctorLoginDto
     * @ return com.zyp.pojo.Doctor
     * @ author DELL
     */
    public Doctor login(DoctorLoginDto doctorLoginDto) {
        String phone = doctorLoginDto.getPhone();
        String password = doctorLoginDto.getPassword();

//        Doctor doctor = doctorMapper.selectByName(name);
        Doctor doctor = doctorMapper.selectByPhone(phone);

        // 账号是否存在
        if(doctor==null){
            throw  new AccountNotFoundException("账号不存在");
        }

        password = Md5Util.getMD5String(password);
        if(!password.equals(doctor.getPassword())){
            throw  new PasswordErrorException("密码错误");
        }

        return doctor;
    }

    /**
     * @ description 修改密码
     * @param updatePasswordDto
     * @ return void
     * @ author DELL
     */
    public void updatePassword(UpdatePasswordDto updatePasswordDto) {
        Long id = ThreadLocalUtil.get();
        String password = updatePasswordDto.getPassword();
        String confirmPassword = updatePasswordDto.getConfirmPassword();
        password = Md5Util.getMD5String(password);
        doctorMapper.updatePassword(id, password, confirmPassword);
    }

    /**
     * @ description 上传头像
     * @param file
     * @ return java.lang.String
     * @ author DELL
     */
    public String updateAvatar(MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String url = MinioUtil.upload(file, minioProperties.getEndpoint(), minioProperties.getPort(), minioProperties.getAccessKey(), minioProperties.getSecretKey(), minioProperties.getBucketName());
        Long id = ThreadLocalUtil.get();
        doctorMapper.updateAvatar(id, url);
        return url;
    }

    public String selectAvatar(){
        long doctorId = ThreadLocalUtil.get();
        String url = doctorMapper.selectAvatar(doctorId);
        if(url==null || url.isEmpty()){
            throw new NotFoundProperties("用户未上传过头像");
        }
        if(url.equals("../../static/imgs/doctor.svg")){
            url = "http://photo.chaoxing.com/photo_80.jpg";
        }
        return url;
    }
}
