package com.zyp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlot implements Serializable {
    private Long id;
    private Long doctorId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Integer slot;
    private Integer available;
    private Integer total;
}
