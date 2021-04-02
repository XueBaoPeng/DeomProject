package com.demo.project.entity;

/**
 * @Description:
 * @Author: xuebaopeng
 * @Date: 2021/4/2 17:05
 */
public enum EmResult {
    // 公共
    SUCCESS("200", "调用成功"),
    FAILED("2001", "调用失败"),
    NO_AUTHORITY("2002", "没有访问权限"),
    FAILED_INVOKE_JSF("2003", "调用杰夫服务失败"),

    INSERT_ERROR_PARAM_REPETITION("3001", "插入数据失败，属性不能重复"),
    PLAT_PARAM_EMPTY("3002","接口传入参数为空"),
    THREAD_PLATFORM_ERROR("3003","调用第三方平台失败"),





    //数据网关
    GATEWAY_NO_AUTHORITY("4001", "没有访问权限"),
    GATEWAY_LIMIT("4002", "当前接口已被限流"),
    GATEWAY_DEGRADE("4003", "当前接口已被降级"),
    GATEWAY_FUSING("4004", "当前接口已被熔断"),
    ;

    private EmResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
