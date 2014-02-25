package ldh.tongxun.security;

import java.util.Map;

public interface ServerSecurity {

	/**
	 * 对返回数据进行签名
	 * @param data
	 * @return
	 */
	public String sign(Map<String, Object> data);
	
	/**
	 * 对来源数据进行验证
	 * @param data
	 * @return
	 */
	public Map<String, Object> vertify(Map<String, Object> data);
}
