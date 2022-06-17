package com.tsh.watermanager.utils;
import com.tsh.watermanager.bean.Manager;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/17 15:47
 * FileName: Tashany
 * Description: JWT工具类
 */
public class JWTUtils {
//    有效时间 2小时
    private static long time = 1000*60*60*2;
    private static  String signature = "manager";

    /**
     * 创建taken
     * @param manager
     * @return
     */
    public static String creatTaken(Manager manager){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("magName", manager.magName)
                .claim("magRoot", manager.magRoot)
                .claim("magImg", manager.magImg)
                .setSubject("manager")
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }

    /**
     * 解析token 判断是taken否有效
     * @param token
     * @return
     */
    public static boolean checkToken(String token){
        if(token == null){
            return false;
        }
        try {
            System.out.println(token);
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            System.out.println(claimsJws.getBody().get("magName"));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 解析token携带的信息 包括 Img name root
     * @param token
     * @return
     */
    public Map getMagTokenInfo(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        Map map = new HashMap();
        map.put("magName", claimsJws.getBody().get("magName"));
        map.put("magRoot", claimsJws.getBody().get("magRoot"));
        map.put("magImg", claimsJws.getBody().get("magImg"));

        return map;
    }
}
