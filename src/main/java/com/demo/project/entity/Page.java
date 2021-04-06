package com.demo.project.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "分页实体类")
public class Page<T> {

	@ApiModelProperty(value = "分页的大小")
	public Long pageSize;
	@ApiModelProperty(value = "分页的第几页")
	public Long pageIndex;
	@ApiModelProperty(value = "总数量")
	public Long totalCount;
	@ApiModelProperty(value = "排序规则")
	public String orderBy;
	@ApiModelProperty(value = "排序类型")
	public String orderType;
	@ApiModelProperty(value = "返回的实体集合")
	public List<T> list;

	public Page() {
	}

	public Page(Long pageIndex, Long pageSize) {
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
	}

}
