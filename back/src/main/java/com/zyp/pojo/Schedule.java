package com.zyp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Serializable {
    private Long id;
    private Long doctorId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Integer AP;
    private Integer surplus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
