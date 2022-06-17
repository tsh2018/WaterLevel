package com.tsh.watermanager.mapper;

import com.tsh.watermanager.bean.Manager;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ManagerMapper {

//    获取管理员列表
    @Select("select * from tb_manager")
    public List<Manager> getManagerList();

//    根据管理员名查找用户id
    @Select("select * from tb_manager where mag_name = #{magName}")
    public Manager getInfoByManagerName(String magName);

//  插入管理员
    @Insert("insert into tb_manager (MAG_NAME,MAG_PASSWARD,MAG_EMAIL,MAG_NUM,MAG_IMG,MAG_ROOT,MAG_CONTENT) values(#{magName},#{magPassward},#{magEmail}," +
            "#{magNum},#{magImg},#{magRoot},#{magContent})")
    public int insertManager(Manager manager);

//  更改头像
    @Update("update tb_manager set MAG_IMG = #{url} where MAG_NAME=#{magName}")
    public int updateManagerImg(String url,String magName);

//    更改个人信息
    @Update("update tb_manager set MAG_NAME=#{magName},MAG_PASSWARD=#{magPassward},MAG_NUM=#{magNum}," +
            "MAG_EMAIL=#{magEmail}, MAG_CONTENT=#{magContent},MAG_ROOT=#{magRoot},MAG_ADDRESS=#{magAddress} " +
            "where MAG_ID=#{magId}")
    public int updateManagerInfo(Manager manager);

//    查看用户名是否存在
    @Select("select * from tb_manager where MAG_NAME=#{magName} and MAG_ID <> #{magId}")
    public List<Manager> findUser(Manager manager);

    @Delete("delete from tb_manager where MAG_ID = #{magId}")
    public int deleteMag(int magId);
}
