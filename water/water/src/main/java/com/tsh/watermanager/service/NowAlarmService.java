package com.tsh.watermanager.service;

import com.tsh.watermanager.bean.AlarmWater;
import com.tsh.watermanager.bean.NowAlarm;
import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.bean.WaterInfo;
import com.tsh.watermanager.mapper.AlarmWaterMapper;
import com.tsh.watermanager.mapper.NowAlarmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 12:15
 * FileName: Tashany
 * Description: 当前警戒水平服务层
 */

@Service
@Slf4j
public class NowAlarmService {

    @Autowired
    NowAlarmMapper alarmMapper;

    @Autowired
    AlarmWaterMapper alarmWaterMapper;

    public Restful getNowAlarm(){
        NowAlarm nowAlarm = alarmMapper.getNowAlarm();
        Map map = new HashMap();
        if (nowAlarm!=null){
            AlarmWater alarmWater = alarmWaterMapper.getAlarmInfoByLevel(nowAlarm.getNowAlarm());
            map.put("alarmWater",alarmWater);
            map.put("updateTime",nowAlarm.getUpdateTime());
            log.info("当前警戒信息获取成功");
            return new Restful(map,"当前警戒信息获取成功","200");
        }else{
            log.info("当前水位获取失败");
            return new Restful("当前警戒信息获取失败","200");
        }
    }

    public Restful updatePoint(NowAlarm nowAlarm){

        int res = alarmMapper.updateDetectPoint(nowAlarm.allPoint, nowAlarm.detectPoint);
        if (res > 0){
            log.info("检测点信息修改成功！");
            return new Restful("检测点信息修改成功！", "200");
        }else{
            log.info("检测点信息修改失败！");
            return new Restful("检测点信息修改失败！", "200");
        }
    }
    public Restful getDetectPoint(){
        NowAlarm nowAlarm = alarmMapper.getNowAlarm();
        Restful restful = new Restful();
        if (nowAlarm != null){
            restful.setObject(nowAlarm);
            restful.setCode("200");
            log.info("检测点信息获取成功！");
            restful.setInfo("检测点信息获取成功");
            return restful;
        }else {
            restful.setCode("400");
            log.info("检测点信息获取失败！");
            restful.setInfo("检测点信息获取失败");
            return restful;
        }
    }
}
