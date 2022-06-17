package com.tsh.watermanager.mapper;

import com.tsh.watermanager.bean.NowAlarm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 12:08
 * FileName: Tashany
 * Description: 当前警戒等级持久层
 */
@Mapper
public interface NowAlarmMapper {

//    @Update("update tb_now_alarm set NOW_ALAEM = #{}, UPDATE_TIME = #{} where NOW_ALARM=#{nowAlarm}")
//    public int setNowAlarm(int nowAlarm,int updateTime);

    @Select("select * from tb_now_alarm")
    public NowAlarm getNowAlarm();

    @Select("select DETECT_POINT from tb_now_alarm")
    public int getDetectPoint();

    @Select("select ALL_POINT from tb_now_alarm")
    public int getAllPoint();

    @Update("update tb_now_alarm set ALL_POINT = #{allPoint},DETECT_POINT = #{detectPoint}")
    public  int updateDetectPoint(int allPoint, int detectPoint);
}
