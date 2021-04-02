package com.demo.project.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 17:04
 */
public class Results<T> {
    public static final String RESULT_SUCCESS = "1";// 成功
    public static final String RESULT_FAIL = "0";// 失败
    public static final List<?> EMPTY = new ArrayList<>();
    protected T data;
    private String result;
    private String code;
    private String msg;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Results success(Object data) {
        Results  resultMap = new Results();
        resultMap.result=RESULT_SUCCESS;
        resultMap.code=EmResult.FAILED.getCode();
        resultMap.msg=EmResult.FAILED.getMsg();
        resultMap.data=data;
        return resultMap;
    }
    public static Results error(Object data) {
        Results  resultMap = new Results();
        resultMap.result=RESULT_FAIL;
        resultMap.code=EmResult.FAILED.getCode();
        resultMap.msg=EmResult.FAILED.getMsg();
        resultMap.data=data;
        return resultMap;
    }

    public boolean isError(){
       return result==RESULT_FAIL;
    }
}
