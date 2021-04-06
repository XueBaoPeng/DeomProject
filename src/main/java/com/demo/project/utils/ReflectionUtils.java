package com.demo.project.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 反射工具类
 * 
 * @author tanxiaolong8
 *
 */
public class ReflectionUtils {

	/***
	 * 递归查找所有的属性
	 * 
	 * @param clz
	 * @param fields
	 * @return
	 */
	private static Set<Field> getFieldAll(Class<?> clz, Set<Field> fields) {
		if (fields == null) {
			fields = new HashSet<>();
		}
		fields.addAll(Stream.of(clz.getDeclaredFields()).collect(Collectors.toList()));
		if (!clz.getSuperclass().equals(Object.class)) {
			getFieldAll(clz.getSuperclass(), fields);
		}
		return fields;
	}

	/**
	 * 递归查找所有的属性
	 * 
	 * @param clz
	 * @return
	 */
	public static List<Field> getFields(Class<?> clz) {
		return new ArrayList<>(getFieldAll(clz, null));
	}

	/***
	 * 返回指定的属性
	 * 
	 * @param clz
	 * @param field
	 * @return
	 */
	public static Field getField(Class<?> clz, String fieldName) {
		for (Field f : getFieldAll(clz, null)) {
			if (f.getName().equals(fieldName)) {
				return f;
			}
		}
		return null;
	}

	public static List<Field> getDeclaredFields(Class<?> clz) {
		return Stream.of(clz.getDeclaredFields()).collect(Collectors.toList());
	}

	public static Object fieldGet(Field field, Object target) throws Exception {
		if (field == null) {
			throw new NullPointerException("fields is empty.");
		}
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		return field.get(target);
	}
}
