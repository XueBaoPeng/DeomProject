package com.demo.project.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.project.common.enums.EmDelStatus;
import com.demo.project.dao.ICrudDao;
import com.demo.project.entity.Entity;
import com.demo.project.entity.QueryCondition;
import com.demo.project.utils.DataUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/***
 * mybatis-plus通用封装service
 * 
 * @author tanxiaolong8
 * @param <D>  抽象dao
 * @param <E>  抽象实体
 * @param <PK> 抽象id
 */
public class CrudService<PK extends Serializable, E extends Entity<PK>, D extends ICrudDao<E, PK>>
		extends ServiceImpl<D, E> {
	
	protected D getDao() {
		return getBaseMapper();
	}


	/**
	 * 获取单条数据
	 *
	 * @param id
	 * @return
	 */

	public E findById(PK id) {
		return (E) getBaseMapper().selectById(id);
	}

	/**
	 * 查询列表数据
	 *
	 * @param entity
	 * @return
	 */
	public List<E> find(E entity) throws Exception  {
		return getBaseMapper().selectList(getCondition(entity));
	}
	
	/**
	 * 查询列表数据
	 *
	 * @param entity
	 * @return
	 */
	public List<E> find(Wrapper<E> wrapper) throws Exception  {
		return getBaseMapper().selectList(wrapper);
	}

	/**
	 * 查询列表某一条数据
	 *
	 * @param entity
	 * @return
	 */
	public E findOne(E entity) throws Exception  {
		return getBaseMapper().selectOne(getCondition(entity));
	}

	/**
	 * 查询所有列表数据
	 *
	 * @param
	 * @return
	 */

	public List<E> findAll() throws Exception {
		return getBaseMapper().selectList(null);
	}

	/**
	 * 查询分页数据
	 *
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  com.demo.project.entity.Page<E> findPage(E entity) throws Exception  {
		Page<E> page = new Page<>(1, 10);
		QueryWrapper<E> wrapper = getCondition(entity);
		com.demo.project.entity.Page<E> pageWrapper = entity.getPage();
		
		if(pageWrapper != null) {
			if(DataUtils.isNotEmpty(pageWrapper.getPageIndex())
					&& DataUtils.isNotEmpty(pageWrapper.getPageIndex())) {
				page.setCurrent(pageWrapper.getPageIndex());
				page.setSize(pageWrapper.getPageSize());
			}
			
			if(DataUtils.isNotEmpty(pageWrapper.getOrderBy())) {
				orderBy(wrapper, pageWrapper.getOrderBy(), pageWrapper.getOrderType());
			}
		} else {
			pageWrapper = new com.demo.project.entity.Page<>();
		}
		IPage<E> ipage = getBaseMapper().selectPage(page, wrapper);
		pageWrapper.setList(ipage.getRecords());
		pageWrapper.setPageIndex(ipage.getCurrent());
		pageWrapper.setPageSize(ipage.getSize());
		pageWrapper.setTotalCount(ipage.getTotal());
		return pageWrapper;
	}

	/**
	 * 查询分页数据(自定义条件)
	 * @param page
	 * @param wrapper
	 * @return
	 * @throws Exception
	 */
	public com.demo.project.entity.Page<E> findPage(com.demo.project.entity.Page<E> pageWrapper, Wrapper<E> wrapper) throws Exception {
		Page<E> page = new Page<>(1, 10);
		if(pageWrapper != null) {
			if(DataUtils.isNotEmpty(pageWrapper.getPageIndex())
					&& DataUtils.isNotEmpty(pageWrapper.getPageIndex())) {
				page.setCurrent(pageWrapper.getPageIndex());
				page.setSize(pageWrapper.getPageSize());
			}
		} else {
			pageWrapper = new com.demo.project.entity.Page<>();
		}
		
		if(DataUtils.isNotEmpty(pageWrapper.getOrderBy())) {
			orderBy(wrapper, pageWrapper.getOrderBy(), pageWrapper.getOrderType());
		}
		IPage<E> ipage = getBaseMapper().selectPage(page, wrapper);
		pageWrapper.setList(ipage.getRecords());
		pageWrapper.setPageIndex(ipage.getCurrent());
		pageWrapper.setPageSize(ipage.getSize());
		pageWrapper.setTotalCount(ipage.getTotal());
		return pageWrapper;
	}

	/***
	 * 查询数量
	 *
	 * @param entity
	 * @return
	 */
	public int findCount(E entity) throws Exception {
		return getBaseMapper().selectCount(getCondition(entity));
	}

	/***
	 * 查询数量(自定义条件)
	 * 
	 * @param wrapper
	 * @return
	 * @throws Exception
	 */
	public int findCount(Wrapper<E> wrapper) throws Exception {
		return getBaseMapper().selectCount(wrapper);
	}

	/**
	 * 保存数据（插入或更新）
	 *
	 * @param entity
	 */
	@Transactional
	public PK saveInfo(E entity)  throws Exception {
		if (DataUtils.isEmpty(entity.getId())) {
			entity.preInsert();
			if (getBaseMapper().insert(entity) > 0) {
				return entity.getId();
			} else {
				return null;
			}
		} else {// update前自动获取version
			E po;
			if (entity.getId() != null && (po = findById(entity.getId())) != null) {
				entity.setVersion(po.getVersion() + 1);
			}
			entity.preUpdate();
			int rst = getBaseMapper().updateById(entity);
			return rst > 0 ? entity.getId() : null;
		}
	}

	@Transactional
	public void saveInfo(List<E> entitys) throws Exception {
		for (E entity : entitys) {
			saveInfo(entity);
		}
	}

	@Transactional
	public int insert(E entity) throws Exception {
		entity.preInsert();
		return getBaseMapper().insert(entity);
	}

	@Transactional
	public void insert(List<E> entitys) throws Exception {
		for (E entity : entitys) {
			insert(entity);
		}
	}
	@Transactional
	public int update(E entity)  throws Exception {
		E po;
//		if (entity.getId() != null && (po = findById(entity.getId())) != null) {
//			entity.setVersion(po.getVersion() + 1);
//		}
		entity.preUpdate();
		return getBaseMapper().updateById(entity);
	}

//	@Transactional
//	public boolean update(E entity, Wrapper<E> wrapper) {
//		E po;
//		if (entity.getId() != null && (po = findById(entity.getId())) != null) {
//			entity.setVersion(po.getVersion() + 1);
//		}
//		entity.preUpdate();
//		return getBaseMapper().update(entity, wrapper);
//	}

	/**
	 * 根据Id删除数据(逻辑删除)
	 *
	 * @param id
	 */
	@Transactional
	public int deleteById(PK id) throws Exception {
		E entity = entityInstance();
		entity.setId(id);
		entity.setDelStatus(EmDelStatus.DELETE.getCode());
		return getBaseMapper().updateById(entity);
	}

	/**
	 * 删除数据(逻辑删除)
	 * @param where
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int delete(E where) throws Exception {
		List<QueryCondition> conditions = where.conditions();
		if (DataUtils.isEmpty(conditions)) {
			return 0;
		}
		E entity = entityInstance();
		entity.setDelStatus(EmDelStatus.DELETE.getCode());
		return getBaseMapper().update(entity, getCondition(conditions));
	}
	
	/**
	 * 删除数据(逻辑删除)
	 * @param where
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int delete(Wrapper<E> where) throws Exception {
		if (DataUtils.isEmpty(where)) {
			return 0;
		}
		E entity = entityInstance();
		entity.setDelStatus(EmDelStatus.DELETE.getCode());
		return getBaseMapper().update(entity, where);
	}

	public QueryWrapper<E> getCondition(E entity) throws Exception {
		QueryWrapper<E> queryWrapper = new QueryWrapper<>();
		entity.conditions().forEach(condition -> {
			queryWrapper.eq(condition.getKey(), condition.getVal());
		});
		if(DataUtils.isNotEmpty(entity.getPage())) {
			boolean isAsc = SqlKeyword.ASC.getSqlSegment().equals(entity.getPage().getOrderType()) ? true : false;
			queryWrapper.orderBy(DataUtils.isNotEmpty(entity.getPage().getOrderBy()), isAsc, entity.getPage().getOrderBy());
		}
		return queryWrapper;
	}

	public QueryWrapper<E> getCondition(List<QueryCondition> conditions) {
		QueryWrapper<E> queryWrapper = new QueryWrapper<>();
		conditions.forEach(condition -> {
			queryWrapper.eq(condition.getKey(), condition.getVal());
		});
		return queryWrapper;
	}

	@SuppressWarnings("unchecked")
	E entityInstance() throws Exception {

		Type superclass = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = null;
		if (superclass instanceof ParameterizedType) {
			parameterizedType = (ParameterizedType) superclass;
			Type[] typeArray = parameterizedType.getActualTypeArguments();
			if (typeArray != null && typeArray.length > 0) {
				Class<?> clz = (Class<?>) typeArray[1];
				return (E) clz.newInstance();
			}
		}
	
		return null;
	}
	
	
	Wrapper<E> orderBy(Wrapper<E> wrapper, String orderby, String sort) {
		
		//lambda方式不支持动态排序字段
		if(wrapper instanceof QueryWrapper) {
			
			boolean isAsc = true;
			if(DataUtils.isNotEmpty(sort)) {
				switch (sort) {
				case "asc":isAsc = true;break;
				default:isAsc = false;break;
				}
			}
			
			((QueryWrapper<E>) wrapper)
			.orderBy(true, isAsc, orderby.split(","));
		}
		return wrapper;
	}
}
