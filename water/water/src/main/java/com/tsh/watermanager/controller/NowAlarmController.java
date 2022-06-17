package com.tsh.watermanager.controller;

import com.tsh.watermanager.bean.NowAlarm;
import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.service.NowAlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 13:43
 * FileName: Tashany
 * Description: 获取当前警戒水位
 */
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/manager")
public class NowAlarmController {

    @Autowired
    NowAlarmService nowAlarmService;

    @GetMapping("/getNowAlarm")
    public Restful getNowAlarm(){
        return nowAlarmService.getNowAlarm();
    }

    @PostMapping("/UpdatePoint")
    public Restful updatePoint(@RequestBody NowAlarm nowAlarm){
        return nowAlarmService.updatePoint(nowAlarm);
    }
    @GetMapping("/getDetectPoint")
    public Restful getDetectPoint(){
        return nowAlarmService.getDetectPoint();
    }
}
