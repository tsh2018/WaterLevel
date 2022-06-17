package com.tsh.watermanager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/31 17:03
 * FileName: Tashany
 * Description: 返回值封装类
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restful {
    private Object object;
    private String info;
    private String code;


    public Restful(String info, String code) {
        this.info = info;
        this.code = code;
    }
}
