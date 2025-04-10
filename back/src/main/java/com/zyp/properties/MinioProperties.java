package com.zyp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "chuangyue.minio")
@Data
public class MinioProperties {
    // 访问的 api 的url
    private String endpoint;
    // api端口号
    private String port;
    // 密钥
    private String accessKey;
    private String secretKey;
    private Boolean secure;
    // 桶名
    private String bucketName;
    // 图片文件的最大大小
    private long imageSize;
    // 文件的最大大小
    private long fileSize;
}
