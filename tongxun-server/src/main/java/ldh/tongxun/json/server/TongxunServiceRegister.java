package ldh.tongxun.json.server;

import java.util.Map;

import ldh.tongxun.json.PathRegister;
import ldh.tongxun.security.ServerSecurity;

public class TongxunServiceRegister implements PathRegister {

	private Map<String, ServerProxy> services;
	
	private Map<String, ServerSecurity> securityMap;
	
	public ServerProxy getBean(String key) {
		if (!services.containsKey(key)) {
			throw new RuntimeException("不包含此bean, key=" + key);
		}
		return services.get(key);
	}
	
	public ServerSecurity getSecurity(String key) {
//		if (!securityMap.containsKey(key)) {
//			throw new RuntimeException("不包含此security, key=" + key);
//		}
		return securityMap.get(key);
	}

	public Map<String, ServerProxy> getServices() {
		return services;
	}

	public void setServices(Map<String, ServerProxy> services) {
		this.services = services;
	}

	public Map<String, ServerSecurity> getSecurityMap() {
		return securityMap;
	}

	public void setSecurityMap(Map<String, ServerSecurity> securityMap) {
		this.securityMap = securityMap;
	}
	
	
}
