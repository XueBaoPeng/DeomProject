package com.demo.project.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @Description: 类编译结果
 * @Author: xuebaopeng
 * @Date: 2021/4/2 16:44
 */

@Getter
@Setter
@Builder
@ToString
public class CompileResult {

    // 主类全类名
    private String mainClassFileName;

    // 编译出来的全类名和对应class字节码
    private Map<String, byte[]> byteCode;

}
