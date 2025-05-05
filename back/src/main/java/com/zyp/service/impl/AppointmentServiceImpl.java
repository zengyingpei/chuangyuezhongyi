package com.zyp.service.impl;

import com.zyp.dto.AddAppointmentDto;
import com.zyp.exception.AppointFailedException;
import com.zyp.mapper.*;
import com.zyp.pojo.*;
import com.zyp.service.AppointmentService;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.AppointmentOfDoctorVo;
import com.zyp.vo.AppointmentVO;
import com.zyp.vo.DoctorVO;
import com.zyp.vo.UserVO;
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
    private ScheduleMapper scheduleMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * @param docId
     * @ description 获取医生一周内的预约情况
     * @ return java.util.List<com.zyp.vo.AppointmentOfDoctorVo>
     * @ author DELL
     */

    public List<AppointmentOfDoctorVo> getAppointmentByDocId(Long docId) {
        List<AppointmentOfDoctorVo> ans = new ArrayList<>();
        // 1、查询时间段表，查看该医生的所有预约情况
        List<Schedule> list = scheduleMapper.selectByDocId(docId, LocalDate.now());

        for (Schedule schedule : list) {
            AppointmentOfDoctorVo appointmentOfDoctorVo = AppointmentOfDoctorVo.builder()
                    .id(schedule.getId())
                    .date(schedule.getDate())
                    .slot(schedule.getAP() == 0 ? "上午" : "下午")
                    .surplus(schedule.getSurplus())
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
        Long scheduleId = addAppointmentDto.getScheduleId();
        Schedule schedule = scheduleMapper.selectById(scheduleId);
        if (schedule.getSurplus() <= 0) {
            log.info("可用人数不足，换一个时间吧");
            throw new AppointFailedException("可用人数不足，换一个时间吧");

        }
        scheduleMapper.reduceRurplus(schedule.getSurplus() - 1, schedule.getId());

        // 2、appointment表需要新增记录
        Long userId = ThreadLocalUtil.get();
        schedule = scheduleMapper.selectById(scheduleId);
        Appointment appointment = Appointment.builder()
                .status(addAppointmentDto.getStatus())
                .doctorId(addAppointmentDto.getDoctorId())
                .userId(userId)
                .date(schedule.getDate())
                .timeSlot(schedule.getAP())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        appointmentMapper.addAppointment(appointment);
    }

    /**
     * @ description 查询所有待就诊
     * @ return java.util.List<com.zyp.vo.AppointmentVO>
     * @ author DELL
     */
    public List<AppointmentVO> selectAllNoFinished() {
        Long userId = ThreadLocalUtil.get();
        User user = userMapper.selectById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        List<Appointment> appointments = appointmentMapper.selectAllNoFinishedByUserId(userId);
        List<AppointmentVO> ans = new ArrayList<>();

        for (Appointment appointment : appointments) {
            Doctor doctor = doctorMapper.selectById(appointment.getDoctorId());
            DoctorVO doctorVO = new DoctorVO();
            BeanUtils.copyProperties(doctor, doctorVO);

            AppointmentVO appointmentVO = AppointmentVO.builder()
                    .id(appointment.getId())
                    .user(userVO)
                    .doctor(doctorVO)
                    .date(appointment.getDate())
                    .slot(appointment.getTimeSlot() == 0 ? "上午" : "下午")
                    .build();
            if (appointment.getStatus() == 0) {
                appointmentVO.setStatus("未付款");
            } else if (appointment.getStatus() == 1) {
                appointmentVO.setStatus("待就诊");
            } else if (appointment.getStatus() == 2) {
                appointmentVO.setStatus("已完成");
            } else if (appointment.getStatus() == 3) {
                appointmentVO.setStatus("超时取消");
            } else {
                appointmentVO.setStatus("拒绝取消");
            }
            ans.add(appointmentVO);
        }
        return ans;
    }

    /**
     * @ description 用户查询所有已经完成的就诊
     * @ return java.util.List<com.zyp.vo.AppointmentVO>
     * @ author DELL
     */
    public List<AppointmentVO> selectAllFinished() {
        Long userId = ThreadLocalUtil.get();
        User user = userMapper.selectById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        List<Appointment> appointments = appointmentMapper.selectAllFinishedByUserId(userId);
        List<AppointmentVO> ans = new ArrayList<>();

        for (Appointment appointment : appointments) {
            Doctor doctor = doctorMapper.selectById(appointment.getDoctorId());
            DoctorVO doctorVO = new DoctorVO();
            BeanUtils.copyProperties(doctor, doctorVO);

            AppointmentVO appointmentVO = AppointmentVO.builder()
                    .id(appointment.getId())
                    .user(userVO)
                    .doctor(doctorVO)
                    .date(appointment.getDate())
                    .slot(appointment.getTimeSlot() == 0 ? "上午" : "下午")
                    .build();
            if (appointment.getStatus() == 0) {
                appointmentVO.setStatus("未付款");
            } else if (appointment.getStatus() == 1) {
                appointmentVO.setStatus("待就诊");
            } else if (appointment.getStatus() == 2) {
                appointmentVO.setStatus("已完成");
            } else if (appointment.getStatus() == 3) {
                appointmentVO.setStatus("超时取消");
            } else {
                appointmentVO.setStatus("拒绝取消");
            }
            ans.add(appointmentVO);
        }
        return ans;
    }

    /**
     * @param id
     * @ description 用户取消
     * @ return void
     * @ author DELL
     */
    @Transactional
    public void cancelAppointment(long id) {

        Appointment appointment = appointmentMapper.selectById(id);
        Long doctorId = appointment.getDoctorId();
        Integer ap = appointment.getTimeSlot();
        LocalDate date = appointment.getDate();

        Schedule schedule = new Schedule();
        schedule.setDoctorId(doctorId);
        schedule.setAP(ap);
        schedule.setDate(date);
        schedule = scheduleMapper.selectByDateAndDocIdAndAP(schedule);
        schedule.setSurplus(schedule.getSurplus() + 1);
        scheduleMapper.update(schedule);
        appointmentMapper.deleteOne(id);
    }

    /**
     * @ description 医生查询所有待就诊
     * @ return java.util.List<com.zyp.vo.AppointmentVO>
     * @ author DELL
     */
    public List<AppointmentVO> selectAllByDoctorId() {
        long doctorId = ThreadLocalUtil.get();
        Doctor doctor = doctorMapper.selectById(doctorId);
        DoctorVO doctorVO = new DoctorVO();
        BeanUtils.copyProperties(doctor, doctorVO);
        List<Appointment> appointments = appointmentMapper.selectAllByDoctorId(doctorId);

        List<AppointmentVO> ans = new ArrayList<>();

        for (Appointment appointment : appointments) {

            User user = userMapper.selectById(appointment.getUserId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);

            AppointmentVO appointmentVO = AppointmentVO.builder()
                    .id(appointment.getId())
                    .user(userVO)
                    .doctor(doctorVO)
                    .date(appointment.getDate())
                    .slot(appointment.getTimeSlot() == 0 ? "上午" : "下午")
                    .build();
            if (appointment.getStatus() == 0) {
                appointmentVO.setStatus("未付款");
            } else if (appointment.getStatus() == 1) {
                appointmentVO.setStatus("待就诊");
            } else if (appointment.getStatus() == 2) {
                appointmentVO.setStatus("已完成");
            } else if (appointment.getStatus() == 3) {
                appointmentVO.setStatus("超时取消");
            } else {
                appointmentVO.setStatus("拒绝取消");
            }
            ans.add(appointmentVO);
        }
        return ans;
    }

    /**
     * @ description 完成接诊
     * @ param id
     * @ return void
     * @ author DELL
     */
    public void finish(long id) {
        appointmentMapper.finish(id);
    }

    /**
     * @ description 医生拒绝接诊，并且说明拒绝原因
     * @ param id
     * @ param reason
     * @ return void
     * @ author DELL
     */
    public void refuse(long id, String reason) {
        appointmentMapper.refuse(id, reason);
    }
}
