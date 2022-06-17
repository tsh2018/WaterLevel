package com.tsh.watermanager.mapper;

import com.tsh.watermanager.bean.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

//    查询用户列表
    @Select("select * from tb_user")
    public List<User> getUserList();

    @Delete("delete from tb_user where USER_ID= #{userId}")
    public int deleteUser(int userId);
}
