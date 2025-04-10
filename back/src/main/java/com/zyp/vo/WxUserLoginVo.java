package com.zyp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxUserLoginVo  implements Serializable {
    private Long id;
    private String openid;
    private String token;
}
