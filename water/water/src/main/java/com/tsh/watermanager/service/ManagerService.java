package com.tsh.watermanager.service;

import com.tsh.watermanager.bean.Manager;
import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.bean.User;
import com.tsh.watermanager.mapper.ManagerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/14 19:03
 * FileName: Tashany
 * Description: 管理员Service类
 */

@Slf4j
@Service
public class ManagerService {

    @Autowired(required = false)
    ManagerMapper managerMapper;

    @Autowired
    LoginService loginService;

    public Restful getManagerList(){
       List<Manager> list =  managerMapper.getManagerList();

       if (list.size()>0){
           log.info("管理员列表查询成功！");
           return new Restful(list,"管理员列表查询成功！","200");
       }else {
           log.info("管理员列表查询失败！");
           return new Restful("管理员列表查询失败！","400");
       }
    }

    public Manager getInfoByManagerName(String magName){
        Manager mag = managerMapper.getInfoByManagerName(magName);
        return mag;
    }

    public int insertManager(Manager manager){
        int res = managerMapper.insertManager(manager);
        return res;
    }

    public int updateManagerImg(String url, String magName){
        return managerMapper.updateManagerImg(url,magName);
    }
    public List<User> getUserList() {
        return null;
    }

    public Map updateManagerInfo(Manager manager){
        System.out.println(manager.toString());
        Map map = new HashMap<>();
//        确保修改的管理员名唯一
        if ( managerMapper.findUser(manager).size() > 0){

            log.info("修改用户名重复");
        }
        if (managerMapper.updateManagerInfo(manager) > 0){
            map.put("msg","管理员信息修改成功！");
            map.put("code","200");
            log.info("管理员信息修改成功");
        }else{
            map.put("msg","管理员信息修改失败！");
            map.put("code","400");
            log.info("管理员信息修改失败");
        }
        return map;
    }

    public Restful deleteManager(int magId){
        if(managerMapper.deleteMag(magId) > 0){
            log.info("管理员删除成功");
            return new Restful("管理员删除成功","200");
        }else{
            return new Restful("管理员删除失败","400");
        }
    }
}
