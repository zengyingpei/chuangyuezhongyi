package com.zyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorReview implements Serializable {
    private Long id;
    private Long userId;
    private Long doctorId;
    private BigDecimal score;
    private String content;
    private LocalDateTime createTime;
}
