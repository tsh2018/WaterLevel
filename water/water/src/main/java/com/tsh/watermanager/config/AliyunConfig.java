package com.tsh.watermanager.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/19 14:11
 * FileName: Tashany
 * Description: 阿里云配置类
 */
@Configuration
@PropertySource(value = {"classpath:application.yml"})
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class AliyunConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String urlPrefix;

    /**
     *创建OSSClient实例。
     * @return
     */
    @Bean
    public OSS oSSClient() {
//        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
