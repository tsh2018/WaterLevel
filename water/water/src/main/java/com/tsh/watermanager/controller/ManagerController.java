package com.tsh.watermanager.controller;

import com.tsh.watermanager.bean.Manager;
import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.bean.User;
import com.tsh.watermanager.service.LoginService;
import com.tsh.watermanager.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/14 19:12
 * FileName: Tashany
 * Description: 管理员Controller层
 */

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/manager")
public class ManagerController{

    @Autowired
    ManagerService managerService;

    @ResponseBody
    @GetMapping("/managerlist")
    public Restful getManagerList(){
        return managerService.getManagerList();
    }

    @ResponseBody
    @GetMapping("/getManagerInfo")
    public Manager getInfoByManagerName(@RequestParam("magName") String magName){
        return managerService.getInfoByManagerName(magName);
    }
    @RequestMapping("/addManager")
    public int insertManager(@RequestBody Manager manager){
       return  managerService.insertManager(manager);
    }

    @PostMapping("/updateManagerInfo")
    public Map updateManagerInfo(@RequestBody Manager manager){
        return  managerService.updateManagerInfo(manager);
    }

    @PostMapping("/deleteManager")
    public Restful deleteManager(@RequestBody HashMap<String, String> map){
        return managerService.deleteManager((Integer.valueOf(map.get("magId"))));
    }
}
