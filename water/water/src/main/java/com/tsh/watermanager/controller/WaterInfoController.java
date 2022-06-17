package com.tsh.watermanager.controller;

import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.service.WaterInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 17:56
 * FileName: Tashany
 * Description: 水位信息控制层
 */

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/manager")
public class WaterInfoController {

    @Autowired
    WaterInfoService waterInfoService;

    @GetMapping("/getLastInfo")
    public Restful getLastInfo(){
        return waterInfoService.getLastInfo();
    }

    @PostMapping("/setInitWaterInfo")
    public Restful setInitWaterInfo(@RequestBody HashMap<String, String> map){
        float waterSpeed = Float.valueOf(map.get("waterSpeed"));
        float waterHeight = Float.valueOf(map.get("waterHeight"));
        float waterAcceleration = Float.valueOf(map.get("waterAcceleration"));
        int id = Integer.valueOf(map.get("id"));
        return waterInfoService.setInitWaterInfo(waterSpeed,waterHeight,waterAcceleration,id);
    }
}
