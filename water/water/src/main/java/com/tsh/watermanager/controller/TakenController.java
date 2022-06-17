package com.tsh.watermanager.controller;

import com.tsh.watermanager.utils.JWTUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/18 16:21
 * FileName: Tashany
 * Description: 校验taken的控制层
 */
@CrossOrigin
@Controller
public class TakenController {

    @ResponseBody
    @GetMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return JWTUtils.checkToken(token);
    }
}
