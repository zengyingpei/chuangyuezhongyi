package com.zyp.controller.user;

import com.zyp.pojo.Medicine;
import com.zyp.pojo.Result;
import com.zyp.service.MedicineService;
import com.zyp.vo.KeepHeathyAndCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;
    /**
     * @ description 获取所有治疗药物(可按名称)
     *
     * @ return com.zyp.pojo.Result<java.util.List<com.zyp.pojo.Medicine>>
     * @ author DELL
     */
    @GetMapping("/list1")
    public Result<List<Medicine>> selectAll1(String name){
        log.info("selectAll1");
        return Result.success(medicineService.selectAll1(name));
    }

    /**
     * @ description 获取养生药物(可按分类和名称)
     *
     * @ return com.zyp.pojo.Result<java.util.List<com.zyp.pojo.Medicine>>
     * @ author DELL
     */
    @GetMapping("/list2")
    public Result<KeepHeathyAndCategoryVo> selectAll2(Long categoryId,String name){
        log.info("selectAll2");
        KeepHeathyAndCategoryVo res = medicineService.selectAll2(categoryId,name);
        return Result.success(res);
    }
}
