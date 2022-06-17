package com.tsh.watermanager.service;

import com.tsh.watermanager.bean.AlarmWater;
import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.bean.WaterInfo;
import com.tsh.watermanager.mapper.WaterInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 17:40
 * FileName: Tashany
 * Description: 水位信息服务层
 */

@Service
@Slf4j
public class WaterInfoService {

    @Autowired
    WaterInfoMapper waterInfoMapper;

    public Restful getLastInfo(){
        WaterInfo waterInfo = waterInfoMapper.getLastInfo();
        if (waterInfo!=null){
            log.info("最新数据获取成功");
            return new Restful(waterInfo,"最新数据获取成功","200");
        }else{
            log.info("最新数据获取失败");
            return new Restful("最新数据获取失败","200");
        }
    }

    public Restful setInitWaterInfo(float waterSpeed, float waterHeight, float waterAcceleration, int id){
        if (waterInfoMapper.setInitWaterInfo(waterSpeed,waterHeight,waterAcceleration,id) > 0){
            log.info("数据更新成功");
            return new Restful("数据更新成功","200");
        }else{
            log.info("数据更新失败");
            return new Restful("数据更新失败","200");
        }
    }

}
