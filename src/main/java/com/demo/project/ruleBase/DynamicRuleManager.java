package com.demo.project.ruleBase;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Description: * 动态规则管理器
 * @Author: xuebaopeng
 * @Date: 2021/4/2 18:00
 */
@Component("dynamicRuleManager")
public class DynamicRuleManager {
    @Autowired
    private JavaRuleStorage javaRuleStorage;
    public Builder builder() {
        return new Builder(this);
    }

    public class  Builder{
        private Rules rules = new Rules();
        private Facts facts = new Facts();
        private RulesEngine engine = new DefaultRulesEngine();
        private JavaRuleStorage javaRuleStorage;
        public Builder(DynamicRuleManager dynamicRuleManager) {
            javaRuleStorage = dynamicRuleManager.javaRuleStorage;
        }

        /**
         * 设置参数，该参数为值传递，在规则里面或者执行完之后可以取到
         * @param name
         * @param value
         * @return
         */
        public Builder setParameter(String name, Object value) {
            facts.put(name, value);
            return this;
        }
        /**
         * 增加规则组（将指定所属分组的所有启用规则添加进来）
         * @param groupName
         * @return
         */
        public Builder addRuleGroup(String groupName) {
            Collection<BaseRule> rs = javaRuleStorage.listObjByGroup(groupName);
            rs.stream().forEach(rules::register);
            return this;
        }

        /**
         * 加载所有的策略
         * @return
         */
        public Builder registerAllRules(){
            Collection<BaseRule> rs = javaRuleStorage.listAllObj();
            rs.stream().forEach(rules::register);
            return this;
         }
        /**
         * 运行规则引擎
         */
        public Builder run() {
            engine.fire(rules, facts);
            return this;
        }

        /**
         * 获取指定参数，并转为指定类型
         * @param pName
         * @param pType
         * @return
         */
        public <T> T getParameter(String pName, Class<T> pType) {
            return facts.get(pName);
        }
    }
}
