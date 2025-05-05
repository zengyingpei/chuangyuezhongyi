package com.zyp.controller.doctor;

import com.zyp.dto.AppointmentDTO;
import com.zyp.dto.IdDTO;
import com.zyp.mapper.AppointmentMapper;
import com.zyp.pojo.Result;
import com.zyp.service.AppointmentService;
import com.zyp.vo.AppointmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/appointment")
public class AppointmentDoctorController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/all")
    public Result<List<AppointmentVO>> selectAllByDoctorId(){
        List<AppointmentVO> ans = appointmentService.selectAllByDoctorId();
        return Result.success(ans);
    }

    @PostMapping("/finish")
    public Result finish(@RequestBody IdDTO idDTO){
        appointmentService.finish(idDTO.getId());
        return Result.success();
    }

    @PostMapping("/refuse")
    public Result refuse(@RequestBody AppointmentDTO appointmentDTO){
        appointmentService.refuse(appointmentDTO.getId() , appointmentDTO.getReason());
        return Result.success();
    }
}
