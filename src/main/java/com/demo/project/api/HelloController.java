package com.demo.project.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 10:43
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String Hello(@RequestParam(value = "name",defaultValue = "World")String name){
        return String.format("Hello %s!",name);
    }
}
