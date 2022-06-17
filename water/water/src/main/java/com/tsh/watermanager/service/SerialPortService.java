package com.tsh.watermanager.service;

import com.alibaba.fastjson.JSON;
import com.tsh.watermanager.bean.AlarmWater;
import com.tsh.watermanager.bean.Restful;
import com.tsh.watermanager.bean.WaterInfo;
import com.tsh.watermanager.bean.WaterPrediction;
import com.tsh.watermanager.controller.WebSocketServer;
import com.tsh.watermanager.mapper.AlarmWaterMapper;
import com.tsh.watermanager.mapper.NowAlarmMapper;
import com.tsh.watermanager.mapper.WaterInfoMapper;
import com.tsh.watermanager.utils.SerialPortUtil;
import gnu.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.IWebContext;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/21 15:48
 * FileName: Tashany
 * Description: 串口数据处理
 */
@Service
public class SerialPortService {
    @Autowired
    NowAlarmMapper nowAlarmMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    WaterInfoMapper waterInfoMapper;

    @Autowired
    AlarmWaterMapper  alarmWaterMapper;

    @Autowired
    WebSocketServer webSocketServer;


    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long nd = 1000 * 24 * 60 * 60;
    private static final long nh = 1000 * 60 * 60;
    private static final long nm = 1000 * 60;

    int flag = 0;//初始数据标志位

//    运行时实现自动执行
    @PostConstruct
    public void serialPortAction() {
        try {
            //设置串口号 波特率
            final SerialPort serialPort = SerialPortUtil.openSerialPort("COM10", 115200);
            ListOperations<String,String> operations = stringRedisTemplate.opsForList();
            ListOperations<String,String> allOperations = stringRedisTemplate.opsForList();
            //初始化第一条数据
            if (flag == 0){
                flag = 1;

                /*清空所有数据*/
                Set<String> keys = stringRedisTemplate.keys("*");
                stringRedisTemplate.delete(keys);

                //从数据库获取最后一条水位数据
                WaterInfo waterInfo = waterInfoMapper.getLastInfo();

                //存入redis
                allOperations.rightPush("alarmLevel",String.valueOf(waterInfo.getWaterNum()));
                allOperations.rightPush("waterHeight",String.valueOf(waterInfo.getWaterHeight()));
                allOperations.rightPush("waterSpeed",String.valueOf(waterInfo.getWaterSpeed()));
                allOperations.rightPush("waterAcceleration",String.valueOf(waterInfo.getWaterAcceleration()));
                allOperations.rightPush("measureTime",dateFormat.format(new Date()));
                allOperations.rightPush("predictionHeight",String.valueOf(String.valueOf(waterInfo.getPredictionHeight())));
            }

            //设置串口的listener
            SerialPortUtil.setListenerToSerialPort(serialPort, event -> {
                //数据通知
                if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                    byte[] bytes = SerialPortUtil.readData(serialPort);
                    System.out.println("收到的数据长度：" + bytes.length);
                    System.out.println("当前时间：" + dateFormat.format(new Date()));
                    getData(new String(bytes));
//                    getWaterInfo();
                }
            });
            try {
                // sleep 一段时间保证线程可以执行完
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | TooManyListenersException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分割接收数据的字符串 截取水位高度
     * @param res
     * @return
     */
    public void getData(String res){
        //获取所有检测点数量
        int allPoint = nowAlarmMapper.getAllPoint();
        //获取可用检测点数量
        int detectPoint = nowAlarmMapper.getDetectPoint();
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
//        数据按行分割
        String[] data = res.split("\n");
        for (int i=0;i<data.length;i++){
//            每行按 ， 分割
            String[] temp = data[i].split(",");
            int pos = temp[0].indexOf("CH");
            String key =   temp[0].substring(pos,pos+4);
            System.out.println("key="+key);
//            存入redis
            opsForValue.set(key,temp[1]);
            opsForList.rightPush("time",dateFormat.format(new Date()));
            //存key到集合
            opsForSet.add("key",key);

            /*redis中存储数据的个数为检测点数*/
            if (opsForList.size("time")  > allPoint){
                opsForList.leftPop("time");
            }
        }

    }
    @Scheduled(fixedRate = 2000)
    public void getWaterInfo(){
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        List<String> time = opsForList.range("time",0,-1);
        WaterInfo waterInfo = new WaterInfo();
        /*返回数据*/
        WaterInfo msg = new WaterInfo();

        int total = 0;
        /*求和*/
        Set<String> keySet = opsForSet.members("key");
        System.out.println(keySet.toString());
        for (String key:keySet
             ) {
            total = total + Integer.parseInt(opsForValue.get(key).trim());
        }
//        System.out.println("和="+total);

        /*平均高度*/
        int avgHeight = (int)total/nowAlarmMapper.getDetectPoint();
//        System.out.println("平均="+total/nowAlarmMapper.getDetectPoint());
        msg.setWaterHeight(avgHeight);

        /*获取两次采集数据的最后一次时间的时间差*/
        List<String> preTime = opsForList.range("measureTime",0,-1);
        Date a = StrToDate(preTime.get(preTime.size()-1));
        Date b = StrToDate(time.get(time.size()-1));
        System.out.println(preTime.size());
        long dur = computationTime( a, b);
//        System.out.println("时间差"+dur);
        DecimalFormat df1 = new DecimalFormat("#.00");//保留两位小数
        msg.setMeasureTime(b);

        /*速度*/
        List<String> preHeight = opsForList.range("waterHeight",0,-1);
//        System.out.println("前一次高度："+Integer.parseInt(preHeight.get(preHeight.size()-1)));
//        System.out.println("高度差："+(avgHeight-Integer.parseInt(preHeight.get(preHeight.size()-1))));
        /********************************************************************************************/
        if (dur == 0) dur = 1;
        /********************************************************************************************/
        float speed = (avgHeight-Integer.parseInt(preHeight.get(preHeight.size()-1).trim()))/dur;
//        System.out.println("速度"+df1.format(speed));
        msg.setWaterSpeed(speed);

        /*加速度*/
        List<String> preSpeed = opsForList.range("waterSpeed",0,-1);
        float acceleration = (speed-Float.parseFloat(preSpeed.get(preSpeed.size()-1).trim()))/dur;
//        System.out.println("加速度"+df1.format(acceleration));
        msg.setWaterAcceleration(acceleration);

        /*5min后的预测高度*/
        int mayHeight = (int)(speed*300+avgHeight);
//        System.out.println("5min后的预测高度"+mayHeight);
        msg.setPredictionHeight(mayHeight);


        /*警戒等级*/
        List<AlarmWater> alarmInfoList = alarmWaterMapper.getAlarmInfo();
        float[] alarmSpeed = new float[5];
        for (AlarmWater sub:alarmInfoList
             ) {
            alarmSpeed[sub.alarmLevel] = (sub.alarmHigh-avgHeight)/sub.alarmTime;
        }

        int  alarmlevel = 5;
        System.out.println("当前速度"+speed);
        for(int i=1; i<5; i++){
            System.out.println(alarmSpeed[i]);
            if (alarmSpeed[i] < speed && speed > 0){
                if (alarmlevel > i){
                    alarmlevel = i;
                }
            }
        }
        System.out.println("当前等级："+alarmlevel);
        System.out.println(alarmInfoList.toString());
        if (alarmlevel != 5){
            int sum = alarmInfoList.get(alarmlevel-1).alarmSum+1;
            try {
                alarmWaterMapper.setAlarmSum(alarmlevel, sum);
            }catch (Exception e){
                System.out.println(e);
            }

            System.out.println(sum);
        }
        System.out.println("当前等级："+alarmlevel);
        msg.setWaterNum(alarmlevel);

        /*到达各警戒等级预测时间*/
        if (stringRedisTemplate.hasKey("predictTime")){
            stringRedisTemplate.delete("predictTime");
        }
        int predictTime = 0;
        if(speed == 0){
            for (int i=0; i<4; i++){
                opsForList.rightPush("predictTime","");
            }
        }else {
            for (AlarmWater sub:alarmInfoList
            ) {
                predictTime = (int)((sub.getAlarmHigh() - avgHeight)/speed);
                opsForList.rightPush("predictTime",String.valueOf(predictTime));
            }
        }

        /*存数据到redis*/
        opsForList.rightPush("waterHeight",String.valueOf(avgHeight));
        opsForList.rightPush("waterSpeed",String.valueOf(speed));
        opsForList.rightPush("waterAcceleration",String.valueOf(acceleration));
        opsForList.rightPush("alarmLevel",String.valueOf(alarmlevel));
        opsForList.rightPush("measureTime",dateFormat.format(new Date()));
        opsForList.rightPush("predictionHeight",String.valueOf(mayHeight));


        if (opsForList.size("waterHeight") > 5) opsForList.leftPop("waterHeight");
        if (opsForList.size("waterSpeed") > 5) opsForList.leftPop("waterSpeed");
        if (opsForList.size("waterAcceleration") > 5) opsForList.leftPop("waterAcceleration");
        if (opsForList.size("alarmLevel") > 5) opsForList.leftPop("alarmLevel");
        if (opsForList.size("measureTime") > 5) opsForList.leftPop("measureTime");
        if (opsForList.size("predictionHeight") > 5) opsForList.leftPop("predictionHeight");
        if (opsForList.size("predictTime") > 4) opsForList.leftPop("predictionHeight");

        /*发送数据*/
        Map res = new HashMap();
        res.put("waterHeight",opsForList.range("waterHeight",0,-1));
        res.put("waterSpeed",opsForList.range("waterSpeed",0,-1));
        res.put("waterAcceleration",opsForList.range("waterAcceleration",0,-1));
        res.put("alarmLevel",opsForList.range("alarmLevel",0,-1));
        res.put("measureTime",opsForList.range("measureTime",0,-1));
        res.put("predictionHeight",opsForList.range("predictionHeight",0,-1));
        res.put("alarmWater",alarmInfoList);
        res.put("predictTime",opsForList.range("predictTime",0,-1));
        Restful restful = new Restful("webSocket发送成功！","200");
        restful.setObject(res);
        webSocketServer.sendMessage(JSON.toJSONString(res));
    }

    /**
     * 计算两个时间段时间差，精确到秒
     * @param startTime
     * @param endTime
     * @return
     */
    public static long computationTime(Date startTime, Date endTime){
            long diff = endTime.getTime() - startTime.getTime();
            System.out.println(startTime);
            long day = diff / nd;
            long hour = diff % nd / nh;
            long min = diff % nd % nh / nm;
            long sec = diff % nd % nh % nm / 1000;
            return day*nd+hour*nh+min*nm+sec;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",java.util.Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
