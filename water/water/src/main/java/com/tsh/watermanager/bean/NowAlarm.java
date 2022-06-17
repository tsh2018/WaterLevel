package com.tsh.watermanager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PublicKey;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/1 11:53
 * FileName: Tashany
 * Description: 当前警戒等级
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NowAlarm {
    public int nowAlarm;
    public int updateTime;
    public int detectPoint;
    public int allPoint;
}
