package com.demo.project.demo.easyRules;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.springframework.util.ResourceUtils;

import javax.json.Json;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 15:05
 */
public class ShopLauncher {

    public static void main(String[] args) throws Exception {
        Persion tom=new Persion("tom",18);
        Facts facts=new Facts();
        facts.put("person",tom);
        //创建规则1
        Rule ageRule=new MVELRule().description("age rules").name("age rules").priority(1)
                .when("person.age > 18 ").then("person.setAdult(true);");

        //创建规则2
        Rule alcholoRule=new MVELRuleFactory(new YamlRuleDefinitionReader()).createRule(new FileReader(ResourceUtils.getFile("classpath:alcohol-rule.yml")));

        Rules rules=new Rules();
        rules.register(ageRule);
        rules.register(alcholoRule);
      //创建规则执行引擎，并执行规则
        RulesEngine rulesEngine=new DefaultRulesEngine();
        System.out.println("Tom: Hi! can I have some Vodka please?");
        rulesEngine.fire(rules,facts);
        System.out.println(JSON.toJSONString(tom));


    }
}
