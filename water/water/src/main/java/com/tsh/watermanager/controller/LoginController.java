package com.tsh.watermanager.controller;

import com.tsh.watermanager.bean.Manager;
import com.tsh.watermanager.service.LoginService;
import com.tsh.watermanager.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/17 14:30
 * FileName: Tashany
 * Description: 登录注册Controller
 */
@Slf4j
@RequestMapping("/api/manager")
@Controller()
public class LoginController {

    @Autowired
    LoginService loginService;

    @ResponseBody
    @PostMapping("/magLogin")
    public Map magLogin(@RequestBody Manager magLogin, HttpSession session){
        Manager manager = loginService.magLogin(magLogin.magName);
        Map map = new HashMap();
//        System.out.println(manager.toString());
        System.out.println(manager != null);
        if ( manager != null && manager.magPassward.equals(magLogin.magPassward) ){
            //登录成功把用户保存起来 利用JWT生成密匙
            manager.setToken(JWTUtils.creatTaken(manager));
            map.put("data",manager);
            map.put("msg","登录成功！");
            map.put("code","200");
            log.info("管理员登录成功");
            return map;
            //登录成功重定向到main.html 解决刷新导致login界面表单重新提交 重定向解决重复提交叫
            //return "redirect:/home";
        }else{
            //写入错误信息
            map.put("msg","用户名或密码错误!");
            map.put("code","401");
            log.info("管理员登录失败");
            return map;
        }

    }

    @ResponseBody
    @PostMapping("/magRegister")
    public Map magRegister(@RequestBody Manager magRegister, HttpSession session){
        System.out.println(magRegister.toString());
        String magName = magRegister.magName;
        Map map = new HashMap();
        System.out.println(loginService.magLogin(magName));
        /*检测用户名是否重复*/
        if (loginService.magLogin(magName) != null){
            map.put("msg","用户名重复！");
            map.put("code","400");
            log.info("管理员注册用户名重复");
            return map;
        }
        int res = loginService.addManager(magRegister);
        if(res >0 ){
            map.put("msg","注册成功！");
            map.put("code","200");
            log.info("管理员注册成功");
        }else{
            map.put("msg","注册失败！");
            map.put("code","501");
            log.info("管理注册失败");
        }
        System.out.println(map.toString());
        return map;
    }
}
