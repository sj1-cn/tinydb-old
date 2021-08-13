package cn.sj1.tinydb.data.jdbc;

public class PojoNameUtils {
	public static String getName(String name, Class<?> clazz) {
		if (clazz == boolean.class) {
			return "is" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
		} else {
			return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
		}
	}
}
