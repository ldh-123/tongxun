package ldh.tongxun.json;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldh.tongxun.json.client.TransferPojo;
import ldh.tongxun.json.server.ServerProxy;
import ldh.tongxun.security.ServerSecurity;
import ldh.tongxun.util.ApplicationContextUtil;
import ldh.tongxun.util.JsonView.JsonViewFactory;

import org.springframework.core.convert.support.DefaultConversionService;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MessageHandle {
	
	private static DefaultConversionService convertService = new DefaultConversionService();
	
	public static String handle(Map<String, Object> data) {
		PathRegister pathRegister = ApplicationContextUtil.getBean(PathRegister.class);
		ServerSecurity security = pathRegister.getSecurity(data.get("path").toString());
		if (security != null) {
			security.vertify(data);	
		}
		
		Object bean = pathRegister.getBean(data.get("path").toString());
		
		ServerProxy serverProxy = (ServerProxy) bean;
		Object returnObject = serverProxy.action(data);
		
		Map<String, Object> dd = new HashMap<String, Object>();
		dd.put("time", System.currentTimeMillis());
		dd.put("DATA", returnObject);
		dd.put("isSuccess", true);
		dd.put("info", "");
		
		String str = null;
		if (security != null) {
			str = security.sign(dd);
		} else {
			str = JsonViewFactory.create().toJson(dd);
		}
		
		return str;
	}
	
	public static TransferPojo parseJson(String json) {
		 JsonElement je = new JsonParser().parse(json);
		 if (je == null || !je.isJsonObject()) {
			 throw new RuntimeException("返回的不是json结构");
		 }
		 JsonObject jo = je.getAsJsonObject();
		 TransferPojo tp = new TransferPojo();
		 fillField(jo, tp, "path");
		 fillField(jo, tp, "type");
		 fillField(jo, tp, "token");
		 if (tp.getType().contains("md5")) {
			 tp.setJsonObject(jo.getAsJsonObject());
		 }
		 return tp;
	 }
	
	private static void fillField(JsonObject json, TransferPojo tp, String key) {
		 JsonElement path = json.get(key);
		 if (path != null && !path.isJsonNull()) {
			 tp.setPath(path.getAsString());
		 } else {
			 throw new RuntimeException("缺少" + key + "字段");
		 }
	 }
	
	public static Object[] params(String json, List<ParamKey> jks, Method method) {
		if (jks.size() > 0) {
			Object[] to = new Object[jks.size()];
			JsonElement je = new JsonParser().parse(json);
			if (je == null || !je.isJsonObject()) {
				throw new RuntimeException("返回的不是json结构");
			}
			
			Class<?>[] ptypes = method.getParameterTypes();
			
			JsonObject jo = je.getAsJsonObject();
			int i=0;
			for (ParamKey jk : jks) {
				JsonElement jf = jo.get(jk.value());
				if (jf.isJsonPrimitive()) {
					to[i] = convertService.convert(jf.getAsString(), ptypes[i]);
				} else if (jf.isJsonArray()) {
					to[i] = JsonViewFactory.create().fromJson(jf, ptypes[i]);
				}
				i++;
			}
			return to;
		}
		return new Object[]{};
	}

	
}
