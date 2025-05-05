package com.zyp.controller.doctor;

import com.zyp.dto.ScheduleAddDTO;
import com.zyp.pojo.Result;
import com.zyp.pojo.Schedule;
import com.zyp.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/doctor/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @GetMapping("/week")
    public Result<List<Schedule>> selectWeek(){
        List<Schedule> schedules = scheduleService.selectWeek();
        return Result.success(schedules);
    }

    @PostMapping("/submit")
    public Result submit(@RequestBody List<ScheduleAddDTO> dtos){
        scheduleService.submit(dtos);
        return Result.success();
    }
}
