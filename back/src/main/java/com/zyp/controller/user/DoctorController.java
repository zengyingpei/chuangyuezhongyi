package com.zyp.controller.user;

import com.mysql.cj.log.Log;
import com.zyp.dto.DoctorDetailDto;
import com.zyp.dto.DoctorPageQueryDto;
import com.zyp.pojo.Doctor;
import com.zyp.pojo.PageBean;
import com.zyp.pojo.Result;
import com.zyp.service.DoctorService;
import com.zyp.vo.DoctorDeatilVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "医生相关的接口")
@RequestMapping("/api/user/doctor")
@Slf4j
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @ApiOperation("根据疾病id查询可以治疗的所有医生(分页查询)")
    @PostMapping()
    public Result<PageBean> selectBySicknessId(@RequestBody DoctorPageQueryDto doctorPageQueryDto){
        PageBean<Doctor> pageResult=doctorService.selectBySicknessId(doctorPageQueryDto);
        return Result.success(pageResult);
    }

    @ApiOperation("获取精选医生的信息")
    @GetMapping("/select_doc")
    public Result<List<Doctor>> selectSelectedDoctor( ){
        List<Doctor> list=doctorService.selectSelectedDoctor();
        return Result.success(list);
    }

    @ApiOperation("获取医生详细信息")
    @PostMapping("/detail")
    public Result<DoctorDeatilVo> selectDoctorDeatil(@RequestBody DoctorDetailDto doctorDetailDto){
        Long id=doctorDetailDto.getId();
        DoctorDeatilVo doctorDeatil=doctorService.selectDoctorDeatil(id);
        return Result.success(doctorDeatil);
    }
}
