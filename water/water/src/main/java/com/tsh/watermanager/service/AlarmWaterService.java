package com.tsh.watermanager.service;

import com.tsh.watermanager.bean.AlarmWater;
import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.bean.WaterInfo;
import com.tsh.watermanager.mapper.AlarmWaterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 11:03
 * FileName: Tashany
 * Description: 警戒水位服务层
 */

@Slf4j
@Service
public class AlarmWaterService {

    @Autowired
    AlarmWaterMapper alarmWaterMapper;

    public Restful setAlarmHigh(int alarmLevel, int alarmHigh){
        System.out.println(alarmLevel);
        System.out.println(alarmHigh);
        int s = alarmWaterMapper.setAlarmHigh(alarmLevel,alarmHigh);
        System.out.println(s);
        if(alarmWaterMapper.setAlarmHigh(alarmLevel,alarmHigh)>0){
            log.info("警戒水位修改成功！");
            return new Restful("警戒水位修改成功","200");
        }else{
            log.info("警戒水位修改失败！");
            return new Restful("警戒水位修改失败","400");
        }
    }

    public Restful setAlarmTime(int alarmLevel, int alarmTime){
        if(alarmWaterMapper.setAlarmTime(alarmLevel,alarmTime)>0){
            log.info("警戒时间修改成功！");
            return new Restful("警戒时间修改成功","200");
        }else{
            log.info("警戒时间修改失败！");
            return new Restful("警戒时间修改失败","400");
        }
    }

    public Restful setAlarmSum(int alarmLevel, int alarmSum){
        if(alarmWaterMapper.setAlarmSum(alarmLevel,alarmSum)>0){
            log.info("警戒次数修改成功！");
            return new Restful("警戒次数修改成功","200");
        }else{
            log.info("警戒次数修改失败！");
            return new Restful("警戒次数修改失败","400");
        }
    }

    public Restful getAlarmInfo(){
        List<AlarmWater> list = alarmWaterMapper.getAlarmInfo();

        if (list!=null){
            log.info("警戒水位信息获取成功！");
            return new Restful(list,"警戒水位信息获取成功","200");
        }else{
            log.info("警戒水位信息获取失败！");
            return new Restful(list,"警戒水位信息获取失败","400");
        }
    }
}
