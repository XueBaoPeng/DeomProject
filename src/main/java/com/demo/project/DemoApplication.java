package com.demo.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 10:04
 */
@SpringBootApplication(scanBasePackages = "com.demo.project")
@MapperScan("com.demo.project.mapper")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
