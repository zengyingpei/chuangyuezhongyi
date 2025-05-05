package com.zyp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleAddDTO implements Serializable {
    private LocalDate date;
    private Integer AP;
    private Integer surplus;
}
