package com.tsh.watermanager.mapper;

import com.tsh.watermanager.bean.AlarmWater;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 10:49
 * FileName: Tashany
 * Description: 警戒水位设置持久层
 */

@Mapper
public interface AlarmWaterMapper {

    @Update("update tb_alarm_water set ALARM_HIGH = #{alarmHigh} where ALARM_LEVEL = #{alarmLevel}")
    public int setAlarmHigh(int alarmLevel,int alarmHigh);

    @Update("update tb_alarm_water set ALARM_TIME = #{alarmTime} where ALARM_LEVEL = #{alarmLevel}")
    public int setAlarmTime(int alarmLevel, int alarmTime);

    @Update("update tb_alarm_water set ALARM_SUM = #{alarmSum} where ALARM_LEVEL = #{alarmLevel}")
    public int setAlarmSum(int alarmLevel, int alarmSum);

    @Select("select * from tb_alarm_water where ALARM_LEVEL = #{alarmLevel}")
    public AlarmWater getAlarmInfoByLevel(int alarmLevel);

    @Select("select * from tb_alarm_water")
    public List<AlarmWater> getAlarmInfo();
}
