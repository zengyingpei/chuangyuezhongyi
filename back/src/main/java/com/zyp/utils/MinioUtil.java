package com.zyp.utils;

import io.minio.*;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
@Slf4j
public class MinioUtil {

    /**
     * @ description 上传
     * @param file 文件
     * @param endPoint 访问的地址
     * @param port 端口号
     * @param accessKey 密钥
     * @param secretKey 密钥
     * @param bucketName 桶名称
     * @ return java.lang.String
     * @ author DELL
     */
    public static String upload(MultipartFile file, String endPoint, String port, String accessKey, String secretKey, String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // 创建minioClient
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endPoint + ":" + port)
                            .credentials(accessKey, secretKey)
                            .build();

            // 如果指定的bucket不存在，则创建，否则使用已有bucket
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                log.info("Bucket {} already exists", bucketName);
            }

            // 获取文件原始名称
            String oringinalName = file.getOriginalFilename();
            // 获取后缀
            String suffix = oringinalName.substring(oringinalName.lastIndexOf('.'));
            // 拼接存储后的文件名
            String fileName = UUID.randomUUID().toString() + suffix;

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            minioClient.putObject(putObjectArgs);
            log.info("文件上传成功");

            // 拼接url并返回
            return endPoint + ":" + port + "/" + bucketName + "/" + fileName;

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
            return "error";
        }
    }
}
