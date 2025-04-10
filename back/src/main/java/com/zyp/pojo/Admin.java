package com.zyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
