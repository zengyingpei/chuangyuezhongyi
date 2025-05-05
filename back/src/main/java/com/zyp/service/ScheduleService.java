package com.zyp.service;

import com.zyp.dto.ScheduleAddDTO;
import com.zyp.pojo.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> selectWeek();

    void submit(List<ScheduleAddDTO> dtos);
}
