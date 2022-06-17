package com.tsh.watermanager.mapper;

import com.tsh.watermanager.bean.WaterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 16:51
 * FileName: Tashany
 * Description: 水位信息持久层
 */

@Mapper
public interface WaterInfoMapper {

    @Select("select * from tb_water_info")
    public WaterInfo getWaterInfo();

    @Select("select * FROM  tb_water_info ORDER BY MEASURE_TIME DESC LIMIT 0,1 ")
    public WaterInfo getLastInfo();

    @Update("update tb_water_info " +
            "set WATER_HEIGHT=#{waterHeight}," +
            "WATER_SPEED = #{waterSpeed}," +
            "WATER_ACCELERATION=#{waterAcceleration}" +
            "where ID=#{id}")
    public int setInitWaterInfo(float waterSpeed, float waterHeight, float waterAcceleration, int id);
}
