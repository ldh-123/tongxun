package ldh.tongxun.json.server;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import ldh.tongxun.json.ParamKey;
import ldh.tongxun.json.MessageHandle;
import ldh.tongxun.json.ServiceProxy;
import ldh.tongxun.security.ServerSecurity;
import ldh.tongxun.util.MethodMd5;

public class ServerProxy extends ServiceProxy {
	
	private Map<String, Method> md5Method;
	
	private Object bean;
	
//	private DefaultConversionService convertService = new DefaultConversionService();
	
	public ServerProxy() {}
	
	public Object action(Map<String, Object> data) {
		String m = data.get("method").toString();
		Method method = md5Method.get(m);
		List<ParamKey> jsonKeys = this.getMethodJsonkeyMap().get(method);
		Object d = data.get("DATA");
		if (d != null) {
			String jsonData = d.toString();
			
			if (jsonKeys.size() > 0) {
				Object[] args = MessageHandle.params(jsonData, jsonKeys, method);
				try {
					return method.invoke(bean, args);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
		try {
			return method.invoke(bean, new Object[]{});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	protected void initMethodMd5() {
		md5Method = MethodMd5.getMd5Method(this.getServiceClass());
	}

	public Map<String, Method> getMd5Method() {
		return md5Method;
	}

	public void setMd5Method(Map<String, Method> md5Method) {
		this.md5Method = md5Method;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

}
