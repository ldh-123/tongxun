package ldh.tongxun.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldh.tongxun.security.ServerSecurity;

import org.springframework.beans.factory.FactoryBean;

public abstract class ServiceProxy {

	private Class<?> serviceClass;
	
	private Map<Method, List<ParamKey>> methodJsonkeyMap;
	
	public ServiceProxy() {}
	
	
	private Class<?> serviceClass(String serviceName) {
		if (serviceClass == null) {
			try {
				serviceClass = Class.forName(serviceName);
				initMethodMd5();
				vertifyMethod();
				System.out.println("service =======================================" + serviceClass);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("");
			}
		}
		return serviceClass;
		
	}

	public void setServiceName(String serviceName) {
		serviceClass(serviceName);
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}

	public Map<Method, List<ParamKey>> getMethodJsonkeyMap() {
		return methodJsonkeyMap;
	}


	protected void vertifyMethod() {
		methodJsonkeyMap = new HashMap<Method, List<ParamKey>>();
		Class<?> clazz = this.getServiceClass();
		while (clazz != null && clazz != Object.class) {
			Method[] methods = clazz.getDeclaredMethods();
			if (methods != null) {
				for (Method m : methods) {
					if (Modifier.isPublic(m.getModifiers())) {
						List<ParamKey> jsonKeys = this.getJsonKeys(m);
						methodJsonkeyMap.put(m, jsonKeys);
					}
				}
			}
			clazz = clazz.getDeclaringClass();
		}
	}

	protected abstract void initMethodMd5();
	
	private List<ParamKey> getJsonKeys(Method method) {
		List<ParamKey> jsonkeys = new ArrayList<ParamKey>();
		Class<?>[] params = method.getParameterTypes();
		
		if (params != null && params.length > 0) {
			Annotation[][] ass = method.getParameterAnnotations();
			if (ass == null || ass.length != params.length) {
				throw new RuntimeException("方法签名不正确，请对参数进行JsonKey注解");
			}
			for (Annotation[] as : ass) {
				ParamKey jsonKey = getJsonKey(as, method);
				jsonkeys.add(jsonKey);
			}
		}
		
		return jsonkeys;
	}
	
	private ParamKey getJsonKey(Annotation[] as,  Method method) {
		for (Annotation a : as) {
			if (a instanceof ParamKey) {
				return (ParamKey) a;
			}
		}
		throw new RuntimeException("请对方法：" + method + "\t进行JsonKey注解");
	}
}
