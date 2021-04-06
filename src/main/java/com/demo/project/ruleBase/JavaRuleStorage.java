package com.demo.project.ruleBase;

import java.util.Collection;

/** * Java规则类存储器
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 17:53
 */

public interface JavaRuleStorage {
    /**
     * 容器是否包含指定规则
     * @param javaRule
     * @return
     */
    boolean contains(String groupName, BaseRule javaRule);

    /**
     * 添加规则到容器
     * @param javaRule
     */
    boolean add(String groupName, BaseRule javaRule);

    /**
     * 批量添加规则到容器的指定组
     * @param javaRules
     */
    boolean batchAdd(String groupName, Iterable<? extends BaseRule> javaRules);

    /**
     * 从容器移除指定规则
     * @param group
     */
    boolean remove(String group, BaseRule rule);

    /**
     * 从容器移除指定组的规则
     * @param group
     */
    boolean remove(String group);

    /**
     * 从容器获取指定组的所有规则
     * @param group
     * @return
     */
    Collection<BaseRule> listObjByGroup(String group);

    /**
     * 从容器获取所有规则
     * @return
     */
    Collection<BaseRule> listAllObj();
}
