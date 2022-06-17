package com.tsh.watermanager.service;

import com.tsh.watermanager.bean.Manager;
import com.tsh.watermanager.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/17 14:24
 * FileName: Tashany
 * Description: 登录注册Service
 */
@Service
public class LoginService {
    @Autowired
    LoginMapper loginMapper;

    /**
     * 登录管理员Service
     * @param magName
     * @return
     */
    public Manager magLogin(String magName){
        return loginMapper.magLogin(magName);
    }

    /**
     * 注册管理员Service
     * @param manager
     * @return
     */
    public int addManager(Manager manager){
        return loginMapper.addManager(manager);
    }
}
