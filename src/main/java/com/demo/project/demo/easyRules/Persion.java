package com.demo.project.demo.easyRules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persion {
    private String name;
    private int age;
    private boolean adult;

    public Persion(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
