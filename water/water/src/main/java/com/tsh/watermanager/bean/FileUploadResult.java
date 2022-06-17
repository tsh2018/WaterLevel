package com.tsh.watermanager.bean;

import lombok.Data;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/19 14:15
 * FileName: Tashany
 * Description: 文件上传结果bean
 */
@Data
public class FileUploadResult {
    // 文件唯一标识
    private String uid;
    // 文件名
    private String name;
    // 状态有：uploading done error removed
    private String status;
    // 服务端响应内容，如：'{"status": "success"}'
    private String response;
}