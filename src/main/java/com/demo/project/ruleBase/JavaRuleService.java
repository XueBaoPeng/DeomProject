package com.demo.project.ruleBase;

import com.demo.project.entity.JavaRuleDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 18:16
 */
@Service
@Slf4j
public class JavaRuleService {
    @Autowired
    private JavaRuleStorage javaRuleStorage;

    @PostConstruct
    public void init() {
        List<JavaRuleDo> javaRules = createJpaQuery();
        javaRules.stream()
                .forEach(javaRule -> {
                    try {
                        BaseRule rule = DynamicRuleUtils.getRuleInstance(javaRule);
                        if (!javaRuleStorage.add(javaRule.getGroupName(), rule)) {
                            log.warn("添加规则{}到容器失败！", javaRule.getName());
                            javaRule.setStatus(1);
                        }
                        log.info("添加了规则{}到容器", javaRule.getFullClassName());
                    } catch (Exception e) {
                        log.warn("添加规则{}到容器异常！", javaRule.getName());
                        javaRule.setStatus(0);
                    }
                });
    }

    public List<JavaRuleDo> createJpaQuery(){
        JavaRuleDo baseRule=new DemoRule1();
        List<JavaRuleDo> result=new ArrayList<>();
        result.add(baseRule);
        return  result;
    }
}
