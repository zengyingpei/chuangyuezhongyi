package com.zyp.controller.admin;

import com.zyp.dto.AdminDoctorPageQueryDto;
import com.zyp.dto.DoctorDto;
import com.zyp.pojo.Doctor;
import com.zyp.pojo.PageBean;
import com.zyp.pojo.Result;
import com.zyp.service.DoctorService;
import com.zyp.vo.DoctorDeatilVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/admin/doctor")
public class AdminDoctorController {
    @Autowired
    private DoctorService doctorService;

    /**
     * @ description G端 医生信息分页查询
     * @param adminDoctorPageQueryDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @GetMapping("/page")
    public Result pageQuery(AdminDoctorPageQueryDto adminDoctorPageQueryDto){
        PageBean<Doctor> pageResult = doctorService.pageQuery(adminDoctorPageQueryDto);
        return Result.success(pageResult);
    }

    /**
     * @ description G端新增医生基础信息
     * @param doctorDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody DoctorDto doctorDto){
        doctorService.insert(doctorDto);
        return Result.success();
    }

    /**
     * @ description 根据医生id删除所有信息
     * @param id
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/delete")
    public Result deleteById(Long id){
        doctorService.deleteById(id);
        return Result.success();
    }

    /**
     * @ description 根据id查询医生的所有信息
     * @param id
     * @ return com.zyp.pojo.Result<com.zyp.vo.DoctorDeatilVo>
     * @ author DELL
     */
    @GetMapping("/select")
    public Result<DoctorDeatilVo> selectById( Long id ){
        return Result.success(doctorService.selectDoctorDeatil(id));
    }

    /**
     * @ description 修改医生的基本信息
     * @param doctorDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/update")
    public Result update(@RequestBody DoctorDto doctorDto){
        doctorService.update(doctorDto);
        return Result.success();
    }
}
