package com.tsh.watermanager.service;

import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.bean.User;

import com.tsh.watermanager.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import java.util.List;

@Slf4j
@Service
public class UserService {
//    默认required=true,表示注入的时候bean必须存在，否则注入失败。
    @Autowired(required = false)
    UserMapper userMapper;

    public Restful getUserList(){
        List<User> list = userMapper.getUserList();

       if (list!=null){
           log.info("用户列表获取成功");
           return new Restful(list,"用户列表获取成功","200");
       }else{
           log.info("用户列表获取失败！");
           return new Restful("用户列表获取失败！","400");
       }
    }

    public Restful deleteUser(int userId){
        if (userMapper.deleteUser(userId) > 0){
            log.info("用户删除成功！");
            return new Restful("用户删除成功！","200");
        }else{
            log.info("用户删除失败！");
            return new Restful("用户删除失败！","400");
        }
    }


}
