package com.tsh.watermanager;

import com.tsh.watermanager.service.SerialPortService;
import com.tsh.watermanager.utils.SerialPortUtil;
import gnu.io.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class WatermanagerApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisConnectionFactory connectionFactory;

    @Autowired
    SerialPortService serialPortService;

    /**
     * 测试串口打开，读，写操作
     */
//    @Test
//    public void serialPortAction() {
//        try {
//            final SerialPort serialPort = SerialPortUtil.openSerialPort("COM2", 115200);
////            //启动一个线程每2s向串口发送数据，发送1000次hello
////            new Thread(() -> {
////                int i = 1;
////                while (i < 1000) {
////                    String s = "hello";
////                    byte[] bytes = s.getBytes();
////                    SerialPortUtil.sendData(serialPort, bytes);//发送数据
////                    i++;
////                    try {
////                        Thread.sleep(2000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }).start();
////            Date date = new Date();
//            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            //设置串口的listener
//            SerialPortUtil.setListenerToSerialPort(serialPort, event -> {
//                ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
//                //数据通知
//                if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//                    byte[] bytes = SerialPortUtil.readData(serialPort);
//                    System.out.println("收到的数据长度：" + bytes.length);
//                    System.out.println("收到的数据：" + new String(bytes));
//                    System.out.println("当前时间：" + dateFormat.format(new Date()));
//                    opsForList.rightPush("height",new String(bytes));
//                    opsForList.rightPush("time",dateFormat.format(new Date()));
//                }
//            });
//            try {
//                // sleep 一段时间保证线程可以执行完
//                Thread.sleep(3 * 30 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | TooManyListenersException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testRedis(){
//        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
//        ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
//        operations.set("hello","world");
//        String hello = operations.get("hello");
//        System.out.println(hello);
//        opsForList.rightPush("height","100");
//        opsForList.rightPush("height","200");
//        opsForList.rightPush("height","300");
//        opsForList.rightPush("time","12:55:21");
//        opsForList.rightPush("time","12:55:23");
//        opsForList.rightPush("time","12:55:23");
//        List<String> list = opsForList.range("height",0, -1);
//        List<String> time = opsForList.range("time",0, -1);
//        System.out.println(list);
//        System.out.println(time);
////        System.out.println(connectionFactory.getClass() );
//    }
//
//    @Test
//    public void test(){
//        serialPortService.serialPortAction();
//    }
//    }
}

