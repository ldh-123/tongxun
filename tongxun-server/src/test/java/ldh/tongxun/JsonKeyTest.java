package ldh.tongxun;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import ldh.tongxun.json.ParamKey;
import ldh.tongxun.json.client.TransferPojo;
import ldh.tongxun.util.TongxunJsonUtil;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonKeyTest {

	public void test(@ParamKey("tt")String tt, @ParamKey("t")String t) {
		System.out.println("hello");
	}
	
	public static void main(String[] args) throws Exception {
		JsonKeyTest jkt = new JsonKeyTest();
		jkt.test3();
	}
	
	public void test3() throws Exception {
		Gson g = new Gson();
		Class<?> clazz = JsonKeyTest.class;
		Method method = clazz.getDeclaredMethod("test", new Class<?>[]{String.class, String.class});
		
		TransferPojo tp = TongxunJsonUtil.createPojo(method, new Object[]{"hello", "world"});
		String json = g.toJson(tp);
		
		JsonElement je = new JsonParser().parse(json);
		JsonObject jo = je.getAsJsonObject();
		JsonElement jo2 = jo.get("data");
		System.out.println(jo2.toString());
		System.out.println(json);
	}
	
	public void test2() throws Exception {
		Class<?> clazz = JsonKeyTest.class;
		Method method = clazz.getDeclaredMethod("test", new Class<?>[]{String.class, String.class});
		Annotation[][] pass = method.getParameterAnnotations();
		for (Annotation[] ps : pass) {
			System.out.println(ps.length);
			for (Annotation p : ps) {
				System.out.println(p);
			}
		}
	}

}
