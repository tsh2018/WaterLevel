package com.tsh.watermanager.controller;

import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.service.AlarmWaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 11:13
 * FileName: Tashany
 * Description: 警戒水位控制层
 */

@Slf4j
@RestController
@RequestMapping("/api/manager")
@CrossOrigin
public class AlarmWaterController {

    @Autowired
    AlarmWaterService alarmWaterService;

    @PostMapping("/setAlarmHigh")
    public Restful setAlarmHigh(@RequestBody HashMap<String, String> map){
        System.out.println(Integer.valueOf(map.get("alarmLevel")));
        System.out.println(Integer.valueOf(map.get("alarmHigh")));
        return alarmWaterService.setAlarmHigh(Integer.valueOf(map.get("alarmLevel")), Integer.valueOf(map.get("alarmHigh")));
    }

    @PostMapping("/setAlarmTime")
    public Restful setAlarmTime(@RequestBody HashMap<String, String> map){
        return alarmWaterService.setAlarmTime(Integer.valueOf(map.get("alarmLevel")), Integer.valueOf(map.get("alarmTime")));
    }

    @PostMapping("/setAlarmSum")
    public Restful setAlarmSum(@RequestBody HashMap<String, String> map){
        return alarmWaterService.setAlarmSum(Integer.valueOf(map.get("alarmLevel")), Integer.valueOf(map.get("alarmSum")));
    }

    @GetMapping("/getAlarmInfo")
    public Restful getAlarmInfo(){
        return alarmWaterService.getAlarmInfo();
    }
}
