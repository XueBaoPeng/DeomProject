package com.demo.project.ruleBase;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 17:54
 */
public class MapJavaRuleStorage implements JavaRuleStorage{
    private final Multimap<String, BaseRule> map = HashMultimap.create();

    @Override
    public boolean contains(String groupName, BaseRule javaRule) {
        return map.containsEntry(groupName, javaRule);
    }

    @Override
    public boolean add(String groupName, BaseRule javaRule) {
        // 如果原来有，就先删除掉
        if (map.containsEntry(groupName, javaRule)) {
            map.remove(groupName, javaRule);
        }
        return map.put(groupName, javaRule);
    }

    @Override
    public boolean batchAdd(String groupName, Iterable<? extends BaseRule> javaRules) {
        return map.putAll(groupName, javaRules);
    }

    @Override
    public boolean remove(String group, BaseRule rule) {
        return map.remove(group, rule);
    }

    @Override
    public boolean remove(String group) {
        return !map.removeAll(group).isEmpty();    }

    @Override
    public Collection<BaseRule> listObjByGroup(String group) {
        return map.get(group);
    }

    @Override
    public Collection<BaseRule> listAllObj() {
        return map.values();
    }
}
