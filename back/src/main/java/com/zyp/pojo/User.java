package com.zyp.pojo;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    //id
    private Long id;
    //微信用户唯一标识
    private String openid;
    //姓名
    private String name;
    //电话
    private String phone;
    //性别 1标识男，0标识女
    private String sex;
    //身份证
    private String idNumber;
    //头像地址
    private String avatar;
    //注册时间
    private LocalDateTime createTime;
}