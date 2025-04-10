package com.zyp.controller.doctor;

import com.zyp.dto.DoctorLoginDto;
import com.zyp.dto.UpdatePasswordDto;
import com.zyp.pojo.Doctor;
import com.zyp.pojo.Result;
import com.zyp.properties.JwtProperties;
import com.zyp.service.DoctorDoctorService;
import com.zyp.utils.JwtUtil;
import com.zyp.vo.DoctorLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/doctor/doctor")
public class DoctorDoctorController {
    @Autowired
    DoctorDoctorService doctorDoctorService;
    @Autowired
    JwtProperties jwtProperties;

    /**
     * @ description 医生端口登录
     * @param doctorLoginDto
     * @ return com.zyp.pojo.Result<com.zyp.vo.DoctorLoginVo>
     * @ author DELL
     */
    @PostMapping("/login")
    public Result<DoctorLoginVo> Login(@RequestBody DoctorLoginDto doctorLoginDto){

        Doctor doctor = doctorDoctorService.login(doctorLoginDto);
        log.info("登录成功{}",doctor);

        // 生成token的载荷
        Map<String,Object> claims =  new HashMap<>();
        claims.put("doctorId", doctor.getId());

        // 生成token
        String token = JwtUtil.createJWT(
                jwtProperties.getDoctorSecretKey(),
                jwtProperties.getDoctorTtl(),
                claims
        );

        // 封装前端数据
        DoctorLoginVo doctorLoginVo = DoctorLoginVo.builder()
                .id(doctor.getId())
                .phone(doctor.getPhone())
                .name(doctor.getName())
                .avatar(doctor.getAvatar())
                .token(token)
                .build();

        return Result.success(doctorLoginVo);
    }

    /**
     * @ description 修改密码
     * @param updatePasswordDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/updatepassword")
    public Result updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto){
        String password = updatePasswordDto.getPassword();
        String confirmPassword = updatePasswordDto.getConfirmPassword();
        log.info("password:{} confirmn_pass:{}",password, confirmPassword);
        if(password.equals(confirmPassword)){
            doctorDoctorService.updatePassword(updatePasswordDto);
            return Result.success();
        }else{
            return Result.error("两次密码输入不一致");
        }
    }

    /**
     * @ description 上传头像
     * @param file
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/updateAvatar")
    public Result updateAvatar(MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        log.info("file={}",file);
        String url = doctorDoctorService.updateAvatar(file);
        if(url==null || url.isEmpty()) return Result.error("上传失败");
        else return Result.success();
    }


    /**
     * @ description 退出登录
     *
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/logout")
    public Result logout(){
        return Result.success();
    }
}
