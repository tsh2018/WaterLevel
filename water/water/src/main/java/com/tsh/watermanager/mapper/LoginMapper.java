package com.tsh.watermanager.mapper;

import com.tsh.watermanager.bean.Manager;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/17 14:15
 * FileName: Tashany
 * Description: 登录注册Mapper
 */

@Mapper
public interface LoginMapper {

    /**
     * 验证管理员登录
     *
     * @param magName
     * @return magPassward
     */
    @Select("select * from tb_manager where MAG_NAME = #{magName}")
    public Manager magLogin(String magName);

    /**
     * 插入管理员
     * @param manager
     * @return
     */
    @Insert("insert into tb_manager (MAG_NAME,MAG_PASSWARD,MAG_EMAIL,MAG_NUM,MAG_IMG,MAG_ROOT,MAG_CONTENT) values(#{magName},#{magPassward},#{magEmail}," +
            "#{magNum},#{magImg},#{magRoot},#{magContent})")
    public int addManager(Manager manager);
}
