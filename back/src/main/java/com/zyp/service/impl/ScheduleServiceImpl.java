package com.zyp.service.impl;

import com.zyp.dto.ScheduleAddDTO;
import com.zyp.mapper.ScheduleMapper;
import com.zyp.pojo.Schedule;
import com.zyp.service.ScheduleService;
import com.zyp.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;


    /**
     * @ description 根据医生id查询一周的安排表
     * @ return java.util.List<com.zyp.pojo.Schedule>
     * @ author DELL
     */
    public List<Schedule> selectWeek() {
        long doctorId = ThreadLocalUtil.get();
        List<Schedule> schedules = scheduleMapper.selectByDocId(doctorId, LocalDate.now());
        return schedules;
    }

    public void submit(List<ScheduleAddDTO> dtos) {
        long doctorId = ThreadLocalUtil.get();
        for (ScheduleAddDTO s : dtos) {
            Schedule schedule = new Schedule();
            schedule.setAP(s.getAP());
            schedule.setDate(s.getDate());
            schedule.setDoctorId(doctorId);

            // 为0删除对应安排记录
            if (s.getSurplus() == 0) {
                scheduleMapper.remove(schedule);
            } else {
                Schedule existSchedule = scheduleMapper.selectByDateAndDocIdAndAP(schedule);
                // 不存在则新增
                if (existSchedule == null) {
                    schedule.setSurplus(s.getSurplus());
                    schedule.setCreateTime(LocalDateTime.now());
                    scheduleMapper.insert(schedule);
                } else {
                    schedule.setSurplus(s.getSurplus());
                    scheduleMapper.update(schedule);
                }
            }
        }
    }
}
