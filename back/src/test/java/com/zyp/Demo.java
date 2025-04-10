package com.zyp;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.zyp.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;


@SpringBootTest
public class Demo {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisTest(){
        System.out.println(redisTemplate);
//        redisTemplate.opsForValue().set("name", "123");
//
//        Object name = redisTemplate.opsForValue().get("name");
//        System.out.println("name = "+ name);
    }
}