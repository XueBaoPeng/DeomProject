package com.demo.project.ruleBase;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 18:03
 */
@Configuration
public class RuleDefaultConf {
    @Bean
    @ConditionalOnMissingBean
    public JavaRuleStorage javaRuleStorage() {
        return new MapJavaRuleStorage();
    }
}
