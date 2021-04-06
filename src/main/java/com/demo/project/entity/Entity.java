package com.demo.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.demo.project.utils.DataUtils;
import com.demo.project.utils.ReflectionUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * entity抽象类 改进点： 将父类的id从原来的具象类型改为抽象类型
 *
 * @author tanxiaolong8
 *
 * @param <PK>
 */
@Getter
@Setter
@ApiModel(value = "公共实体基类")
public class Entity<PK> extends BaseEntity<PK> {

    static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "删除标识")
    @TableField("del_status")
    private Integer delStatus;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建人用户名")
    @TableField("creator_user_name")
    private String creatorUserName;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_date")
    private Date createdDate;

    @ApiModelProperty(value = "修改人用户名")
    @TableField("modified_user_name")
    private String modifiedUserName;

    @ApiModelProperty(value = "修改时间")
    @TableField("modified_date")
    private Date modifiedDate;

    @TableField(exist = false)
    private Page page;

    @ApiModelProperty(value = "版本号")
    @Version
    @TableField("`version`")
    Integer version;// 版本号


    /**
     * 前端可伸缩行数据的操作标识 0：新增，1：修改，2：删除
     **/
    @TableField(exist = false)
    private Integer operationStatus;
    @TableField(exist = false)
    private String likeKey;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            locale = "zh",
            timezone = "GMT+8"
    )
    public Date getCreatedDate() {
        return createdDate;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            locale = "zh",
            timezone = "GMT+8"
    )
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            locale = "zh",
            timezone = "GMT+8"
    )
    public Date getModifiedDate() {
        return modifiedDate;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            locale = "zh",
            timezone = "GMT+8"
    )
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    @Override
    public void preInsert() {
        // 不限制ID为UUID，调用setisInsert()使用自定义ID
        this.delStatus = 0;
//		if (DataUtils.isEmpty(this.creatorUserName)) {
////			Session session = SessionContext.getSession();
////			if (session == null) {
        this.creatorUserName = "unknown";
//			} else {
////				this.creatorUserName = session.getErp();
//			}
//		}
        this.id = null;
        this.modifiedUserName = this.creatorUserName;
        this.createdDate = new Date();
        this.modifiedDate = this.createdDate;
        this.version = 1;
    }

    public boolean insertFlg() {
        return id == null;
    }

    public boolean updateFlg() {
        return !insertFlg();
    }

    @Override
    public void preUpdate() {
//		if (this.modifiedErp == null) {
//			Session session = SessionContext.getSession();
//			if (session == null) {
        this.creatorUserName = "unknown";
//			} else {
//				this.modifiedErp = session.getErp();
//			}
//		}
        this.modifiedDate = new Date();
    }

    @Override
    public void preDelete() {
        this.delStatus = 1;
//		if (this.modifiedErp == null) {
//			Session session = SessionContext.getSession();
//			if (session == null) {
        this.modifiedUserName = "unknown";
//			} else {
//				this.modifiedErp = session.getErp();
//			}
//		}
        this.modifiedDate = new Date();
    }

	/***
	 * 动态查询条件支持
	 *
	 * @return
	 * @throws Exception
	 */
	public List<QueryCondition> conditions() throws Exception {

		List<QueryCondition> funcs = new ArrayList<>();
		List<Field> fields = ReflectionUtils.getFields(this.getClass());
		for (Field field : fields) {
			TableField annotation = field.getAnnotation(TableField.class);
			Object val = ReflectionUtils.fieldGet(field, this);
			if (val != null && annotation != null && DataUtils.isNotEmpty(annotation.value())) {
				QueryCondition condition = new QueryCondition();
				condition.setKey(annotation.value());
				condition.setVal(val);
				funcs.add(condition);
			}
		}

		return funcs;
	}
}
