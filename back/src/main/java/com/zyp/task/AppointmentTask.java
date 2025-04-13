package com.zyp.task;

import com.zyp.mapper.AppointmentMapper;
import com.zyp.mapper.TimeSlotMapper;
import com.zyp.pojo.Appointment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
@Slf4j
public class AppointmentTask {
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private TimeSlotMapper timeSlotMapper;

    /**
     * @ description  检查超过十分钟没有付款的预约订单
     * @ return void
     * @ author DELL
     */
    @Scheduled(cron = "0 * * * * ? ")   //每秒钟第0秒执行
    @Transactional
    public void autoInsertNewTimeSlot() {
        log.info("start task");
        //计算获得十分钟前
        LocalDateTime time = LocalDateTime.now().plusMinutes(-10);
        // 查找订单状态是未付款，时间是十分钟前
        List<Appointment> list = appointmentMapper.selectAllTimeOut(0, time);
        // 存在这种订单
        if (list != null && !list.isEmpty()) {
            for (Appointment appointment : list) {
                //修改订单状态
                appointmentMapper.updateStatusTimeOut(appointment.getId());
                // 时间段名额加1
                timeSlotMapper.addAvailable(appointment.getDoctorId(), appointment.getDate(), appointment.getTimeSlot());
            }
        }
    }
}
