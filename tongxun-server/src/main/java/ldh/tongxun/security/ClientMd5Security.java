package ldh.tongxun.security;

import java.util.Map;

import ldh.tongxun.util.TongxunJsonUtil;
import ldh.util.encrypt.EncryptUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ClientMd5Security implements ClientSecurity {

	private String salt;
	
	public static final String TOKEN = "TOKEN";
	public static final String DATA = "DATA";
	
	@Override
	public Map<String, Object> sign(Map<String, Object> data) {
		String json = data.get(DATA).toString();
		String md5 = EncryptUtil.md5(json + "__" + salt);
		data.clear();
		data.put(TOKEN, md5);
		return data;
	}

	@Override
	public Object vertify(String json) {
		JsonElement je = new JsonParser().parse(json);
		if (je == null || !je.isJsonObject()) {
			throw new RuntimeException("返回的不是json结构");
		}
		JsonObject jo = je.getAsJsonObject();
		
		String token = TongxunJsonUtil.getStrField(jo, "token", true);
		long time = TongxunJsonUtil.getLongField(jo, "time");
		JsonElement data = jo.get("data");
		String sb = data.toString() + "_" + time;
		String md5 = EncryptUtil.md5(sb + "__" + salt);
		if (!md5.equals(token)) {
			throw new RuntimeException("签名验证失败");
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
