package com.tsh.watermanager.controller;

import com.tsh.watermanager.bean.Manager;
import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.bean.User;
import com.tsh.watermanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/manager")
public class UserController{

    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/userlist")
    public Restful getUserList(){
        return userService.getUserList();
    }

    @PostMapping("/deleteUser")
    public Restful deleteUser(@RequestBody HashMap<String, String> map){
        return userService.deleteUser(Integer.valueOf(map.get("userId")));
    }


}
