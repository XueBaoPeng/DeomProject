package com.demo.project.common.enums;

/**
 * 删除状态枚举
 */
public enum EmDelStatus {

    NOT_DELETE(0),
    DELETE(1),
    ;

	Integer code;
	
    EmDelStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
