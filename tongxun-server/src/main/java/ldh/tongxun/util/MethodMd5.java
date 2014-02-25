package ldh.tongxun.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import ldh.util.encrypt.EncryptUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodMd5 {

	private static final Logger logger = LoggerFactory.getLogger(MethodMd5.class);
	
	public static Map<Method, String> getMethodMd5(Class<?> clazz) {
		Map<Method, String> map = new HashMap<Method, String>();
		while (clazz != null && clazz != Object.class) {
			Method[] methods = clazz.getDeclaredMethods();
			if (methods != null) {
				for (Method m : methods) {
					if (Modifier.isPublic(m.getModifiers())) {
						StringBuilder sb = new StringBuilder();
						String name = m.getName();
						
						sb.append(name).append(":");
						Class<?>[] args = m.getParameterTypes();
						if (args != null) {
							for (Class<?> c : args) {
								sb.append(c.getSimpleName()).append("-");
							}
						}
						try {
							String md5 = EncryptUtil.md5(sb.toString().getBytes("utf-8"));
							map.put(m, md5);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			clazz = clazz.getDeclaringClass();
		}
		
		return map;
	}
	
	public static Map<String, Method> getMd5Method(Class<?> clazz) {
		Map<String, Method> map = new HashMap<String, Method>();
		while (clazz != null && clazz != Object.class) {
			Method[] methods = clazz.getDeclaredMethods();
			if (methods != null) {
				for (Method m : methods) {
					if (Modifier.isPublic(m.getModifiers())) {
						StringBuilder sb = new StringBuilder();
						String name = m.getName();
						
						sb.append(name).append(":");
						Class<?>[] args = m.getParameterTypes();
						if (args != null) {
							for (Class<?> c : args) {
								sb.append(c.getSimpleName()).append("-");
							}
						}
						try {
							String md5 = EncryptUtil.md5(sb.toString().getBytes("utf-8"));
							logger.info(md5 + "\t" + m);
							map.put(md5, m);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						
					}
				}
			}
			
			clazz = clazz.getDeclaringClass();
		}
		
		return map;
	}
}
