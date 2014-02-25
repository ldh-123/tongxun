package ldh.tongxun.json.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;

import ldh.tongxun.json.ParamKey;
import ldh.tongxun.json.ServiceProxy;
import ldh.tongxun.security.ClientSecurity;
import ldh.tongxun.util.HttpClientHelp;
import ldh.tongxun.util.JsonView.JsonViewFactory;
import ldh.tongxun.util.MethodMd5;
import ldh.tongxun.util.TongxunJsonUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ClientProxy extends ServiceProxy implements InvocationHandler, FactoryBean {
	
	private String host;
	
	private String path;
	
	private ClientSecurity security;
	
	private Map<Method, String> methodMd5;
	
	public ClientProxy() {}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (!getMethodJsonkeyMap().containsKey(method)) {
			return method.invoke(this, args);
		}
		Map<String, Object> data = paramMap(method, args);
		String json = paramsJson(data);
		data.put("DATA", json);
		if (security != null) {
			security.sign(data);
		}
		data.put("path", path);
		data.put("method", methodMd5.get(method));
		
		String response = HttpClientHelp.post(host, data);
		
		JsonElement je = null;
		if (security != null) {
			je =  (JsonElement) security.vertify(response);
		} else {
			je = TongxunJsonUtil.parseJson(response);
		}
		Class<?> type = method.getReturnType();
		if (type != void.class) {
			return JsonViewFactory.create().fromJson(je, type);
		}
		return null;
	}
	
	protected String paramsJson(Map<String, Object> data) {
		String json = JsonViewFactory.create().toJson(data);
		return json;
	}

	@Override
	public Object getObject() throws Exception {
		return Proxy.newProxyInstance(  
                this.getClass().getClassLoader(),  
                new Class<?>[]{this.getServiceClass()},  
                this);
	}
	
	@Override
	public Class<?> getObjectType() {
		return this.getServiceClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setSecurity(ClientSecurity security) {
		this.security = security;
	}

	@Override
	protected void initMethodMd5() {
		methodMd5 = MethodMd5.getMethodMd5(this.getServiceClass());
	}
	
	private Map<String, Object> paramMap(Method method, Object[] args) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<ParamKey> jsonKeys = this.getMethodJsonkeyMap().get(method);
		int i=0;
		if (jsonKeys != null) {
			for (ParamKey jsonKey : jsonKeys) {
				data.put(jsonKey.value(), args[i++]);
			}
		}
		return data;
	}
}
