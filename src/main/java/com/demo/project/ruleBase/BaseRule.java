package com.demo.project.ruleBase;

import com.demo.project.entity.JavaRuleDo;
import org.jeasy.rules.annotation.Priority;

import java.util.Objects;

/**
 * 强制所有规则类都要继承我们定义好的规则基类，有两个原因：
 *
 * 1、定义优先级属性，并提供set方法供外部设值、get方法供规则引擎获取。
 *
 * 2、重写hashCode方法和equals方法，以让容器（HashSet）认定相同类型的两个元素是相同的。
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 17:51
 */

public class BaseRule extends JavaRuleDo {

    private int priority = Integer.MAX_VALUE;
    /*重写equals方法和hashCode方法，让Set集合判定同类型的两个对象相同*/
    @Override
    public boolean equals(Object obj) {
        return Objects.nonNull(obj)
                && Objects.equals(this.getClass().getName(), obj.getClass().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getClass().getName());
    }

    /**
     * 获取优先级
     */
    @Priority
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
