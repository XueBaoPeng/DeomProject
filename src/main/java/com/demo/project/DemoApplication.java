package com.demo.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 10:04
 */
@SpringBootApplication(scanBasePackages = "com.demo.project")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
