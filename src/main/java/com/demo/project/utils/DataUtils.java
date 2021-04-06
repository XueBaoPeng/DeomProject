package com.demo.project.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/***
 * 工具类
 * 
 * @author tanxiaolong8
 *
 */
public class DataUtils {
	
	public static final String EMPTY = "";

	public static boolean isNotEmpty(String text) {
		return !isEmpty(text);
	}

	public static boolean isEmpty(String text) {
		if (text == null) {
			return true;
		}
		return text.length() == 0;
	}

	public static boolean isEmpty(Integer i) {
		if (i == null) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(Integer i) {
		return !isEmpty(i);
	}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}
	
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	public static boolean isEmpty(Set<?> set) {
		return set == null || set.size() == 0;
	}
	
	public static boolean isNotEmpty(Set<?> set) {
		return !isEmpty(set);
	}

	public static boolean isEmpty(Map map) {
		return map == null || map.size() == 0;
	}
	
	public static boolean isNotEmpty(Map map) {
		return !isEmpty(map);
	}

	public static boolean isEmpty(Object... objs) {
		if (objs == null) {
			return true;
		}
		for (Object obj : objs) {
			if (obj == null)
				return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(Object... objs) {
		return !isEmpty(objs);
	}

	/**
	 * 是否可转化为数字
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNum(Object o) {
		try {
			new BigDecimal(o.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	public static boolean isNumber(Object obj) {
		return !isEmpty(obj) && obj.toString().trim().matches("^[+\\-]?[0-9]+[.]?[0-9]*[E]?[0-9]*$");
	}
	/**
	 * 转化为int型数字, 不可转化时返回0
	 * 
	 * @param o
	 * @return
	 */
	public static int toInt(Object o) {
		if (isNum(o)) {
			return new Integer(o.toString());
		} else {
			return 0;
		}
	}

	public static long toLong(String str) {
		long value = 0;
		if (!isEmpty(str) && isNum(str)) {
			try {
				value = Long.parseLong(str);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static float toFloat(String str) {
		float value = 0;
		if (!isEmpty(str) && isNum(str)) {
			try {
				value = Float.parseFloat(str);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static double toDouble(String str) {
		double value = 0;
		if (!isEmpty(str) && isNum(str)) {
			try {
				value = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static void addList(List all, List add) {
		if (all == null) {
			return;
		}

		if (add == null || add.size() < 1) {
			return;
		}
		all.addAll(add);
	}

	public static String connect(List<String> strs, String split) {
		StringBuffer str = new StringBuffer("");
		if (strs != null && strs.size() > 0 && split != null) {
			for (String s : strs) {
				if (s != null && s.length() > 0) {
					str.append(s);
				}
				if (split.length() > 0) {
					str.append(split);
				}
			}
			if (str.length() > 0 && split.length() > 0) {
				str.delete(str.length() - split.length(), str.length());
			}
		}
		return str.toString();
	}

	public static String md5(String text) {
		try {
			byte[] bytes = text.getBytes("utf-8");
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(bytes);
			bytes = messageDigest.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				if ((bytes[i] & 0xff) < 0x10) {
					sb.append("0");
				}

				sb.append(Long.toString(bytes[i] & 0xff, 16));
			}
			return sb.toString().toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public static String uuid() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	
	
	
	// obj -> any type 
	public static String obj2str(Object param) {
		if(isEmpty(param)) {
			return null;
		}
		return String.valueOf(param);
	}

	public static <K, V> Map<K, V> obj2Map(Object obj) {
		return isEmpty(obj) ? null : (Map)obj;
	}

	public static <K, V> V obj2Map(Map<K, V> obj, K key) {
		if (isEmpty((Object)obj)) {
			return null;
		} else {
			return isEmpty(obj.get("key")) ? null : obj.get("key");
		}
	}


	public static Integer obj2int(Object param) {
		if(isEmpty(param) || !(param instanceof Integer)) {
			return null;
		}
		
		return (int)param;
	}
	
	public static Long obj2long(Object param) {
		if(isEmpty(param) || !(param instanceof Long || param instanceof Integer)) {
			return null;
		}
		return Long.valueOf(param + "");
	}
	
	
	// str -> any type 
	public static Integer str2int(String param) {
		try {
			return Integer.valueOf(param);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	//int -> any type
	public static String int2str(Integer param) {
		if(isEmpty(param)) {
			return null;
		}
		return param + "";
	}
}
