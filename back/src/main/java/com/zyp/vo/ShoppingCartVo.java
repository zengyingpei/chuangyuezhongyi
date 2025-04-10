package com.zyp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ShoppingCartVo implements Serializable {
    //主键
    private Long id;
    private Long medicineId;
    //数量
    private Integer number;
    //名称（冗余字段）
    private String name;
    //图片（冗余字段）
    private String image;
    //在当前数量下的 总金额
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;
}
