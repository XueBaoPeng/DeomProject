package com.demo.project.api;

import com.demo.project.ruleBase.DynamicRuleManager;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    DynamicRuleManager dynamicRuleManager;


    @GetMapping("/hello")
    public String Hello(@RequestParam(value = "name",defaultValue = "World")String name){
        return String.format("Hello %s!",name);
    }

    @GetMapping("/rule")
    public String rule(@RequestParam(value = "name",defaultValue = "World")String name){
        DynamicRuleManager.Builder builder = dynamicRuleManager.builder()
                .setParameter("param1", "Hello")
                .setParameter("param2", "World")
                .addRuleGroup("testRule")
                .run();
        String param1 = builder.getParameter("param1", String.class);
        String param2 = builder.getParameter("param2", String.class);
        System.out.println(param1 + " " + param2);
        return String.format("Hello %s!",param1 + " " + param2);
    }
}
