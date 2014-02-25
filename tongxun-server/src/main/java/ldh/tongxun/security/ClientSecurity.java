package ldh.tongxun.security;

import java.util.Map;

public interface ClientSecurity {

	/**
	 * 对请求数据进行签名
	 * @param data
	 * @return
	 */
	public Map<String, Object> sign(Map<String, Object> data);
	
	/**
	 * 对放回数据进行验证
	 * @param json
	 * @return
	 */
	public Object vertify(String json);
}
