package com.zyp.controller.common;

import com.zyp.exception.UploadErrorException;
import com.zyp.pojo.Result;
import com.zyp.properties.MinioProperties;
import com.zyp.utils.AliOssUtil;
import com.zyp.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class FileUploadController {
    @Autowired
    private MinioProperties minioProperties;

    /**
     * @ description 文件上传
     * @param file
     * @ return com.zyp.pojo.Result<java.lang.String>
     * @ author DELL
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {

        String url = MinioUtil.upload(file, minioProperties.getEndpoint(), minioProperties.getPort(), minioProperties.getAccessKey(), minioProperties.getSecretKey(), minioProperties.getBucketName());
        log.info("url= {}",url);

        if(url.isEmpty() || url.equals("error")){
            throw new UploadErrorException("文件上传失败");
        }else{
            return Result.success(url);
        }
    }
}
