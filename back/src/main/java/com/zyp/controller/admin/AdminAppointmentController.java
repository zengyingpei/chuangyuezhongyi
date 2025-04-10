package com.zyp.controller.admin;

import com.zyp.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin/appointment")
public class AdminAppointmentController {
    public Result addNew(){
        return Result.success();
    }
}
