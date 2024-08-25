package com.openicu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 云奇迹
 */
@SpringBootApplication
@Configurable
@EnableScheduling
@EnableDubbo
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }

}
