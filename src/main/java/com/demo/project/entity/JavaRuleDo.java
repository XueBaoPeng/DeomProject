package com.demo.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @Description:所有属性都对应着数据库JAVA_RULE表的字段
 * @Author: xuebaopeng
 * @Date: 2021/4/2 15:31
 */

@Getter
@Setter
@ToString
@EntityScan
@TableName(value = "java_rule")
@ApiModel(value = "主要用来接收自定义的动态加载easyRule的规则实体类")
public class JavaRuleDo extends Entity<java.lang.Integer> {

    @ApiModelProperty(value = "目标，一般指哪个系统",required = true)
    // 目标，一般指哪个系统
    @TableField("target")
    private String target;
    // 文件名
    @ApiModelProperty(value = "文件名",required = true)
    @TableField("file_name")
    private String fileName;
    // 全类名
    @ApiModelProperty(value = "全类名",required = true)
    @TableField("full_class_name")
    private String fullClassName;
    // 类名
    @ApiModelProperty(value = "类名",required = true)
    @TableField("simple_class_name")
    private String simpleClassName;
    // 源码
    @ApiModelProperty(value = "源码",required = true)
    @TableField("src_code")
    private String srcCode;
    // 编译后字节码
    @ApiModelProperty(value = "编译后字节码",required = true)
    @TableField("byte_content")
    private byte[] byteContent;
    // 状态，1有效 0无效
    @ApiModelProperty(value = "状态，1有效 0无效",required = true)
    @TableField("status")
    private Integer status;
    // 组别名称，一般指哪一系列规则
    @ApiModelProperty(value = "组别名称，一般指哪一系列规则",required = true)
    @TableField("group_name")
    private String groupName;
    // 顺序（优先级）
    @ApiModelProperty(value = "顺序（优先级）",required = true)
    @TableField("sort")
    private Integer sort;
    // 规则名称
    @ApiModelProperty(value = "规则名称",required = true)
    @TableField("name")
    private String name;
}
