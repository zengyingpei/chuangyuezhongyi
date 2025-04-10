package com.zyp.controller.user;

import com.zyp.dto.AddAppointmentDto;
import com.zyp.pojo.Result;
import com.zyp.service.AppointmentService;
import com.zyp.vo.AppointmentOfDoctorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    /**
     * @ description 获取医生一周内的预约情况
     * @param docId
     * @ return com.zyp.pojo.Result<java.util.List<com.zyp.vo.AppointmentOfDoctorVo>>
     * @ author DELL
     */
    @GetMapping("/list")
    public Result<List<AppointmentOfDoctorVo>> getAppointmentByDocId(Long docId){
        log.info("获取医生一周内的预约情况{}",docId);
        List<AppointmentOfDoctorVo> list= appointmentService.getAppointmentByDocId(docId);
        return Result.success(list);
    }

    /**
     * @ description 新增预约
     * @param addAppointmentDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/add")
    public Result addAppointment(@RequestBody AddAppointmentDto addAppointmentDto){
        log.info("addAppointment的controller{}",addAppointmentDto);
        appointmentService.addAppointment(addAppointmentDto);
        return Result.success();
    }
}
