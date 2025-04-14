package com.zyp.service.impl;

import com.zyp.dto.AddAppointmentDto;
import com.zyp.exception.AppointFailedException;
import com.zyp.mapper.AppointmentMapper;
import com.zyp.mapper.DoctorMapper;
import com.zyp.mapper.TimeSlotMapper;
import com.zyp.pojo.Appointment;
import com.zyp.pojo.Doctor;
import com.zyp.pojo.TimeSlot;
import com.zyp.service.AppointmentService;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.AppointmentOfDoctorVo;
import com.zyp.vo.AppointmentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    @Autowired
    private DoctorMapper doctorMapper;

    /**
     * @param docId
     * @ description 获取医生一周内的预约情况
     * @ return java.util.List<com.zyp.vo.AppointmentOfDoctorVo>
     * @ author DELL
     */

    public List<AppointmentOfDoctorVo> getAppointmentByDocId(Long docId) {
        List<AppointmentOfDoctorVo> ans = new ArrayList<>();
        // 1、查询时间段表，查看该医生的所有预约情况
        List<TimeSlot> list = timeSlotMapper.selectByDocId(docId, LocalDate.now());
        for (TimeSlot timeSlot : list) {
            AppointmentOfDoctorVo appointmentOfDoctorVo = AppointmentOfDoctorVo.builder()
                    .id(timeSlot.getId())
                    .date(timeSlot.getDate())
                    .slot(timeSlot.getSlot() == 0 ? "上午" : "下午")
                    .available(timeSlot.getAvailable())
                    .total(timeSlot.getTotal())
                    .build();
            ans.add(appointmentOfDoctorVo);
        }
        return ans;
    }

    /**
     * @param addAppointmentDto
     * @ description 新增预约
     * @ return void
     * @ author DELL
     */
    @Transactional
    public void addAppointment(AddAppointmentDto addAppointmentDto) {

        // 1、time_slot表的可用名额需要减少
        Long slotId = addAppointmentDto.getSlotId();
        TimeSlot slot = timeSlotMapper.selectById(slotId);
        if (slot.getAvailable() <= 0) {
            log.info("可用人数不足，换一个时间吧");
            throw new AppointFailedException("可用人数不足，换一个时间吧");

        }
        timeSlotMapper.reduceAvailable(slotId);

        // 2、appointment表需要新增记录
        Long userId = ThreadLocalUtil.get();
        TimeSlot timeSlot = timeSlotMapper.selectById(slotId);
        Appointment appointment = Appointment.builder()
                .status(addAppointmentDto.getStatus())
                .doctorId(addAppointmentDto.getDoctorId())
                .userId(userId)
                .date(timeSlot.getDate())
                .timeSlot(timeSlot.getSlot())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        appointmentMapper.addAppointment(appointment);
    }

    public List<AppointmentVO> selectAll() {
        Long userId = ThreadLocalUtil.get();
        List<Appointment> appointments = appointmentMapper.selectAllByUserId(userId);
        List<AppointmentVO> ans = new ArrayList<>();

        for (Appointment appointment : appointments) {
            Doctor doctor = doctorMapper.selectById(appointment.getDoctorId());

            AppointmentVO appointmentVO = AppointmentVO.builder()
                    .doctorName(doctor.getName())
                    .doctorId(doctor.getId())
                    .doctorPhone(doctor.getPhone())
                    .date(appointment.getDate())
                    .slot(appointment.getTimeSlot() == 0 ? "上午" : "下午")
                    .build();
            if (appointment.getStatus() == 0) {
                appointmentVO.setStatus("未付款");
            } else if (appointment.getStatus() == 1) {
                appointmentVO.setStatus("待就诊");
            } else if (appointment.getStatus() == 2) {
                appointmentVO.setStatus("已完成");
            } else {
                appointmentVO.setStatus("超时取消");
            }
            ans.add(appointmentVO);
        }
        return ans;
    }
}
