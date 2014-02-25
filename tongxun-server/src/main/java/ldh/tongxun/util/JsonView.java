package ldh.tongxun.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;


public class JsonView {
	
	private static Logger logger = Logger.getLogger(JsonView.class);
	private Gson gson;
	private Map<String, Object> data = new HashMap<String, Object>();
	private boolean isSuccess;
	private String info;
	private GsonBuilder builder = new GsonBuilder();
	
	public JsonView() {
		init();
	}
	
	public JsonView put(String key, Object message) {
		if (key.contains("isSuccess") || key.contains("info")) {
			throw new RuntimeException("please use success() or fail()");
		}
		data.put(key, message);
		return this;
	}
	
	public JsonView beanInclude(String key, Object bean, String...includes) {
		if (key.contains("isSuccess") || key.contains("info")) {
			throw new RuntimeException("please use success() or fail()");
		}
		Map<String, Object> map = Util.beanToMap(bean);
		Map<String, Object> input = new HashMap<String, Object>();
		
		if (map != null && map.size() > 0) {
			if (includes != null && includes.length > 0) {
				for (String p : includes) {
					input.put(p, map.get(p));
				}
				if (input.size() > 0) {
					put(key, input);
				}
			} else {
				put(key, map);
			}
		}
		return this;
	}
	
	public JsonView beanExclude(String key, Object bean, String...excludes) {
		if (key.contains("isSuccess") || key.contains("info")) {
			throw new RuntimeException("please use success() or fail()");
		}
		Map<String, Object> map = Util.beanToMap(bean);
		
		if (map != null && map.size() > 0) {
			if (excludes != null && excludes.length > 0) {
				for (String p : excludes) {
					map.remove(p);
				}
			}
		}
		if(map.size() > 0) {
			put(key, map);
		}
		return this;
	}
	
//	public JsonView fail(String message) {
//		this.isSuccess = false;
//		this.info = message;
//		return this;
//	}
	
	public JsonView info(String message) {
		this.isSuccess = true;
		this.info = message;
		return this;
	}
	
	public JsonView success(boolean isSuccess) {
		this.isSuccess = isSuccess;
		return this;
	}
	
	public String toJson() {
		createGson();
		fill();
		try {
			return gson.toJson(data);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String toJson(Object data) {
		createGson();
		fill();
		try {
			return gson.toJson(data);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String toJsonTree(Object data) {
		createGson();
		fill();
		try {
			return gson.toJsonTree(data).toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String toJson(Object data, Type type) {
		createGson();
		fill();
		try {
			return gson.toJson(data, type);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String toJsonTree(Object data, Type type) {
		createGson();
		fill();
		try {
			return gson.toJsonTree(data, type).toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		createGson();
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return gson.fromJson(jsonString, clazz);
		} catch (Exception e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	public <T> T fromJson(String jsonString, Type type) {
		createGson();
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		
		try {
			return gson.fromJson(jsonString, type);
		} catch (Exception e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	public <T> T fromJson(JsonElement jsonElement, Type type) {
		createGson();
		if (jsonElement == null) {
			return null;
		}
		
		try {
			return gson.fromJson(jsonElement, type);
		} catch (Exception e) {
			logger.warn("parse json string error:" + jsonElement, e);
			return null;
		}
	}
	
	/** 
     * 设置转换日期类型的format pattern,如果不设置默认打印Timestamp毫秒数. 
     */  
    public JsonView setDateFormat(String pattern) {  
        if (StringUtils.isNotBlank(pattern)) {   
            builder.setDateFormat(pattern);
        } 
        return this;
    } 
    
    public JsonView setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {  
        if (fieldNamingStrategy != null) {   
            builder.setFieldNamingStrategy(fieldNamingStrategy);
        } 
        return this;
    } 
    
    public JsonView setFieldNamingStrategy(ExclusionStrategy... fxclusionStrategies) {  
        if (fxclusionStrategies != null) {   
            builder.setExclusionStrategies(fxclusionStrategies);
        } 
        return this;
    }
    
    public JsonView registerTypeAdapter(Type type, Object object) {  
        if (type != null || object != null) {   
            builder.registerTypeAdapter(type, object);
        }
        return this;
    }
    
    private void createGson() {
    	if (gson == null) gson = builder.create();
    }
    
    private void init() {
    	builder.serializeNulls();
    }
    
    private void fill() {
    	data.put("isSuccess", isSuccess);
    	data.put("info", info);
    }
	
	public static final class JsonViewFactory {
		
		public static JsonView create() {
			return new JsonView();
		}
		
	}
}
