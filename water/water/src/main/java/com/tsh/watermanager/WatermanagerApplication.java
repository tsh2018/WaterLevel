package com.tsh.watermanager;

import com.tsh.watermanager.service.SerialPortService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.tsh.watermanager.mapper")
@ServletComponentScan(basePackages = "com.tsh.watermanager") //指定原生servlet组件都放在哪里
@SpringBootApplication
@EnableScheduling //开启定时器
public class WatermanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatermanagerApplication.class, args);
    }


}
