package com.wong.ffwb.scheduling_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.wong.ffwb.scheduling_system.dao")
public class SchedulingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingSystemApplication.class, args);
    }

}
