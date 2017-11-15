package com.livingguide.common.utils.pageutil;

import java.lang.reflect.Field;

/**
 * @author HXJ
 *
 */
public class ReflectHelper {

	/**
	 * 根据对象属性的fieldName获取Field对象
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException {
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 根据对象属性的fieldName获取
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			SecurityException, NoSuchFieldException {
		Object value = null;
		Field field = getFieldByFieldName(obj, fieldName);
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws IllegalArgumentException,
			IllegalAccessException, SecurityException, NoSuchFieldException {
		Field field = getFieldByFieldName(obj, fieldName);
		if (field != null) {
			if (field.isAccessible()) {
				field.set(obj, value);
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
		}
	}
}
