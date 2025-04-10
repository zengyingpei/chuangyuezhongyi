package com.zyp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medicine implements Serializable {
    private Long id;
    private String name;
    private String image;
    //价格
    private BigDecimal price;
    //类型
    private Integer type;
    //描述
    private String description;
    //生产日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateInPro;
    //有效日期
    private String effDate;
    //库存
    private Integer number;
    //是否打折
    private Integer onSale;
}
