package ldh.tongxun.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ldh.tongxun.json.ParamKey;
import ldh.tongxun.json.client.TransferPojo;
import ldh.util.encrypt.EncryptUtil;

public class TongxunJsonUtil {

	public static ParamKey getJsonKey(Annotation[] as) {
		for (Annotation a : as) {
			if (a instanceof ParamKey) {
				return (ParamKey)a;
			}
		}
		throw new RuntimeException("请对参数注解, jsonKey");
	}
	
	public static JsonElement parseJson(String response) {
		JsonElement je = new JsonParser().parse(response);
		if (je == null || !je.isJsonObject()) {
			throw new RuntimeException("返回的不是json结构");
		}
		JsonObject jo = je.getAsJsonObject();
		boolean isSuccess = getBooleanField(jo, "isSuccess");
		String info = getStrField(jo, "info", false);
		if (!isSuccess) {
			throw new RuntimeException(info);
		}
		JsonElement data = jo.get("DATA");
		return data;
	}
	
	public static TransferPojo createPojo(Method method, Object[] args) {
		TransferPojo tp = new TransferPojo();
		Class<?>[] pts = method.getParameterTypes();
		int paramLength = 0;
		if (pts != null) {
			paramLength = pts.length;
		}

		if (paramLength > 0) {
			Annotation[][] pass = method.getParameterAnnotations();
			if (pass != null && pass.length > 0) {
				Map<String, Object> data = new HashMap<String, Object>();
				if (pass.length != args.length) {
					throw new RuntimeException(method + "\t参数注解缺少！");
				}
				int i=0;
				for (Annotation[] ps : pass) {
					ParamKey jsonKey = getJsonKey(ps);
					String key = jsonKey.value();
					data.put(key, args[i++]);
				}
				tp.setData(data);
			} else if (paramLength == 1) {
				tp.setBean(args[0]);
			} else {
				throw new RuntimeException(method + "\t参数注解缺少！");
			}
		}
		
		return tp;
	}
	
	public static String getStrField(JsonObject json, String key, boolean require) {
		 JsonElement path = json.get(key);
		 if (path != null && !path.isJsonNull()) {
			 return path.getAsString();
		 } else {
			 if (require) {
				 throw new RuntimeException("缺少" + key + "字段"); 
			 }
		 }
		 return null; 
	}
	
	public static Boolean getBooleanField(JsonObject json, String key) {
		 JsonElement path = json.get(key);
		 if (path != null && !path.isJsonNull()) {
			 return path.getAsBoolean();
		 } else {
			 throw new RuntimeException("缺少" + key + "字段");
		 }
	 }
	
	public static Long getLongField(JsonObject json, String key) {
		 JsonElement path = json.get(key);
		 if (path != null && !path.isJsonNull()) {
			 return path.getAsLong();
		 } else {
			 throw new RuntimeException("缺少" + key + "字段");
		 }
	 }
}
