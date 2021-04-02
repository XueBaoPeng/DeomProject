package com.demo.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:所有属性都对应着数据库JAVA_RULE表的字段
 * @Author: xuebaopeng
 * @Date: 2021/4/2 15:31
 */

@Getter
@Setter
@ToString
@EntityScan
@TableName(value = "JAVA_RULE")
public class JavaRuleDo implements Serializable {
    private static final long serialVersionUID = 830103606495004702L;
    @TableField("id")
    private Long id;
    // 目标，一般指哪个系统
    @TableField("target")
    private String target;
    // 文件名
    @TableField("file_name")
    private String fileName;
    // 全类名
    @TableField("full_class_name")
    private String fullClassName;
    // 类名
    @TableField("simple_className")
    private String simpleClassName;
    // 源码
    @TableField("src_code")
    private String srcCode;
    // 编译后字节码
    @TableField("byte_content")
    private byte[] byteContent;
    // 创建时间
    @TableField("create_time")
    private Date createTime;
    // 创建用户id
    @TableField("create_user_id")
    private String createUserId ;
    // 创建用户名称
    @TableField("create_user_name")
    private String createUserName;
    // 更新时间
    @TableField("update_time")
    private Date updateTime;
    // 更新用户id
    @TableField("update_user_id")
    private String updateUserId;
    // 更新用户名称
    @TableField("update_user_name")
    private String updateUserName;
    // 是否已删除，1是 0否
    @TableField("is_deleted")
    private Integer isDeleted = 0;
    // 状态，1有效 0无效
    @TableField("status")
    private Integer status =1;
    // 组别名称，一般指哪一系列规则
    @TableField("group_name")
    private String groupName;
    // 顺序（优先级）
    @TableField("sort")
    private Integer sort = Integer.MAX_VALUE;
    // 规则名称
    @TableField("name")
    private String name;
    // 规则描述
    @TableField("description")
    private String description;

}
