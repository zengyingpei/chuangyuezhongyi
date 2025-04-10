package com.zyp.controller.user;

import com.zyp.pojo.Result;
import com.zyp.pojo.Sickness;
import com.zyp.service.SicknessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "疾病相关接口")
@RequestMapping("/api/user/sickness")
public class SicknessController {
    @Autowired
    private SicknessService sicknessService;
    @ApiOperation("根据科室id查询所属的所有疾病")
    @GetMapping("")
    public Result<List<Sickness>> selectByDeptId(@RequestParam Long deptId){
        List<Sickness> list=sicknessService.selectByDeptId(deptId);
        return Result.success(list);
    }
}
