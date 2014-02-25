package ldh.tongxun.json;

import ldh.tongxun.security.ServerSecurity;

public interface PathRegister {

	public Object getBean(String path);
	
	public ServerSecurity getSecurity(String type);
}
