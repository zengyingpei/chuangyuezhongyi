package com.zyp.controller.user;

import com.zyp.dto.AddAppointmentDto;
import com.zyp.dto.IdDTO;
import com.zyp.pojo.Result;
import com.zyp.service.AppointmentService;
import com.zyp.vo.AppointmentOfDoctorVo;
import com.zyp.vo.AppointmentVO;
import io.netty.channel.PreferHeapByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
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
        appointmentService.addAppointment(addAppointmentDto);
        return Result.success();
    }

    /**
     * @ description 查询用户的所有待就诊预约
     *
     * @ return com.zyp.pojo.Result<java.util.List<com.zyp.vo.AppointmentVO>>
     * @ author DELL
     */
    @GetMapping("/nofinished")
    public Result<List<AppointmentVO>> selectAllNoFinished(){
        return Result.success(appointmentService.selectAllNoFinished());
    }

    @GetMapping("/finished")
    public Result<List<AppointmentVO>> selectAllFinished(){
        return Result.success(appointmentService.selectAllFinished());
    }

    @PostMapping("/cancel")
    public Result cancelAppointment(@RequestBody IdDTO idDTO){
        appointmentService.cancelAppointment(idDTO.getId());
        return Result.success();
    }
}
