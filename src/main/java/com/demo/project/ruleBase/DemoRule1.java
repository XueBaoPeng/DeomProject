package com.demo.project.ruleBase;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 18:04
 */
@Rule
public class DemoRule1  extends BaseRule{

    @Condition
    public boolean when(@Fact("param1") String param1) {
        System.out.println("我是参数1，value=" + param1);
        return true;
    }

    @Action
    public void then(@Fact("param2") String param2) {
        System.out.println("我是参数2，value=" + param2);
    }
}
