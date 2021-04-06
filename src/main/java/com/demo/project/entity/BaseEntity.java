package com.demo.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public abstract class BaseEntity<PK> implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id",type = IdType.AUTO)
	protected PK id;
	
    /**
     * 插入之前执行方法，子类实现
     */
    public abstract void preInsert();

    /**
     * 更新之前执行方法，子类实现
     */
    public abstract void preUpdate();

    /**
     * 逻辑删除之前执行方法，子类实现
     */
    public abstract void preDelete();

}
