package com.tsh.watermanager.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.tsh.watermanager.bean.FileUploadResult;
import com.tsh.watermanager.service.FileUploadService;
import com.tsh.watermanager.service.ManagerService;
import com.tsh.watermanager.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/19 15:13
 * FileName: Tashany
 * Description: 上传文件
 */
@Slf4j
@CrossOrigin
@Controller
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    public ManagerService managerService;

    /**
     * 文件上传到oss
     * @param uploadFile
     * @return map
     * @throws Exception
     */
    @PostMapping("file/upload")
    @ResponseBody
    public Map upload(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request)
            throws Exception {
        Map map = new HashMap<>();
//        获取前端传来的请求头 taken
        String taken = request.getHeader("token");
//        调用jwtUtils工具类
        JWTUtils jwtUtils = new JWTUtils();
        Map takenMap = new HashMap<>(jwtUtils.getMagTokenInfo(taken));
        String magName = (String) takenMap.get("magName");
        FileUploadResult res = this.fileUploadService.upload(uploadFile);
        //存入文件信息
        map.put("data",res);
//        记录状态信息
        if (res.getResponse().equals("success")){
            //将图片url存入数据库
            String url = fileUploadService.getUrl(res.getName());
            if(managerService.updateManagerImg(url,magName) > 0){
                map.put("msg","图片上传成功！");
                map.put("code","200");
                map.put("url", url);
                log.info("图片上传成功");
            }
        }else {
            map.put("msg","图片上传失败！");
            map.put("code","400");
            log.info("图片上传失败");
        }
        return map;
    }

    /**
     * @return FileUploadResult
     * @desc 根据文件名删除oss上的文件
     * @Param objectName
     */
    @RequestMapping("file/delete")
    @ResponseBody
    public FileUploadResult delete(@RequestParam("fileName") String objectName)
            throws Exception {
        return this.fileUploadService.delete(objectName);
    }

    /**
     * @desc 查询oss上的所有文件
     * @return List<OSSObjectSummary>
     * @Param
     */
    @RequestMapping("file/list")
    @ResponseBody
    public List<OSSObjectSummary> list()
            throws Exception {
        return this.fileUploadService.list();
    }

    /**
     * @desc 根据文件名下载oss上的文件
     * @return
     * @Param objectName
     */
    @RequestMapping("file/download")
    @ResponseBody
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        //通知浏览器以附件形式下载
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(objectName.getBytes(), "ISO-8859-1"));
        this.fileUploadService.exportOssFile(response.getOutputStream(),objectName);
    }
}
