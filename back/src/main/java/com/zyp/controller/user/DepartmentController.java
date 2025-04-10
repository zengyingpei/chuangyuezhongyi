package com.zyp.controller.user;

import com.zyp.pojo.Department;
import com.zyp.pojo.Result;
import com.zyp.service.DepartmentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "科室相关接口")
@RequestMapping("/api/user/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping()
    public Result<List<Department>> select(){
        List<Department> list=departmentService.select();
        return Result.success(list);
    }
}
