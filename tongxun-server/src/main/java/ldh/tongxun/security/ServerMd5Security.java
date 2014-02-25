package ldh.tongxun.security;

import java.util.HashMap;
import java.util.Map;

import ldh.tongxun.util.JsonView.JsonViewFactory;
import ldh.util.encrypt.EncryptUtil;

public class ServerMd5Security implements ServerSecurity {

	private String salt;
	
	public static final String TOKEN = "TOKEN";
	public static final String DATA = "DATA";
	
	@Override
	public String sign(Map<String, Object> data) {
		Object d = data.get(DATA);
		String t = data.get("time").toString();
		Map<String, Object> dd = new HashMap<String, Object>();
		dd.put("time", t);
		dd.put(DATA, d);
		String sb = JsonViewFactory.create().toJson(dd);
		String md5 = EncryptUtil.md5(sb + "__" + salt);
		data.put("token", md5);
		data.put("isSuccess", true);
		data.put("info", "");
		String json = JsonViewFactory.create().toJson(data);
		return json;
	}

	@Override
	public Map<String, Object> vertify(Map<String, Object> data) {
		String json = data.get(DATA).toString();
		String md5 = EncryptUtil.md5(json + "__" + salt);
		String token = data.get(TOKEN).toString();
		if (!md5.equals(token)) {
			throw new RuntimeException("数据验签错误");
		}
		return data;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	

}
