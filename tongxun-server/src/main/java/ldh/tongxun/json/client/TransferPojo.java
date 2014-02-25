package ldh.tongxun.json.client;

import java.util.Map;

import com.google.gson.JsonObject;

public class TransferPojo {
	
	private String type;
	
	private String path;
	
	private Map<String, Object> data;
	
	private Object bean;
	
	private JsonObject jsonObject;

	public TransferPojo() {}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public JsonObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	
}
