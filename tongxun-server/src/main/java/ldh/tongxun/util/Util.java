package ldh.tongxun.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import ldh.tongxun.util.JsonView.JsonViewFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Util {

	public static final String DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 将json转化成jsonResponse
	 * @param ajaxResponse
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T>JsonResponse<T> toJsonResponse(String ajaxResponse, Class<T> clazz) {
		JsonResponse<T> jr = new JsonResponse<T>();
		JsonElement je = new JsonParser().parse(ajaxResponse);
		if (je == null || !je.isJsonObject()) {
			throw new RuntimeException("返回的不是json结构");
		}
		JsonObject jo = je.getAsJsonObject();
		
		JsonElement success = jo.get("isSuccess");
		if (success != null && !success.isJsonNull()) {
			jr.setSuccess(success.getAsBoolean());
		}
		
		JsonElement info = jo.get("info");
		if (info != null && !info.isJsonNull()) jr.setInfo(info.getAsString());
		
		JsonElement bean = jo.get("bean");
		if (bean != null) {
			if (bean.isJsonObject()) {
				T t = toBean(bean, clazz);
				jr.setBean(t);
			}else if (bean.isJsonArray()) {
				List<T> ts = toBeans(bean, clazz);
				jr.setBeans(ts);
			} else if (bean.isJsonNull()) {
				//不处理
			} else {
				throw new RuntimeException("json暂时不支持此类型！");
			}
		}
		
		JsonElement beans = jo.get("beans");
		if (beans != null) {
			if (beans.isJsonArray()) {
				List<T> ts = toBeans(beans, clazz);
				jr.setBeans(ts);
			} else if (beans.isJsonNull()) {
				//不处理
			} else {
				throw new RuntimeException("json暂时不支持此类型！");
			}
		}
		
		return jr;
	}
	
	private static <T>T toBean(JsonElement je, Class<T> clazz) {
		if (!je.isJsonObject()) {
			throw new RuntimeException("jsonElements must ben isJsonObject");
		}
		JsonObject jo = je.getAsJsonObject();
		T t = JsonViewFactory.create().setDateFormat(Util.DATE_FORMATE).fromJson(jo, clazz);
		return t;
	}
	
	private static <T> List<T> toBeans(JsonElement je, Class<T> clazz) {
		if (!je.isJsonArray()) {
			throw new RuntimeException("jsonElements must ben isJsonArray");
		}
		JsonArray jo2 = je.getAsJsonArray();
		List<T> beanValues = new ArrayList<T>(jo2.size());
		for (JsonElement joo : jo2) {
			T tvalue = JsonViewFactory.create().setDateFormat(Util.DATE_FORMATE).fromJson(joo, clazz);
			beanValues.add(tvalue);
		}
		return beanValues;
	}
	
//	private static Type getReturnType(Class clazz, String methodName, Class[] params, int idx) throws Exception {
//		Method method = clazz.getMethod(methodName, params);
//		Type returnType = method.getGenericReturnType();
//		if  (returnType  instanceof  ParameterizedType) {
//            Type[] types  =  ((ParameterizedType)returnType).getActualTypeArguments();
//            return types[idx];
//        } else {
//        	return returnType;
//        }
//	}
	
	/**
	 * 将订单信息组合成url的参数形式
	 * @param bean  订单信息或sortedMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String urlParam(Object bean) {
		StringBuffer sb = new StringBuffer();
		
		Map<String, Object> map = null;
		if (bean instanceof SortedMap) {
			map = (SortedMap<String, Object>) bean;
		} else {
			 map = beanToMap(bean);
		}
		for (Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (value == null) continue;
			String strValue = objectToString(value);
			if (strValue.equals("")) continue;
			sb.append(entry.getKey()).append("=").append(strValue).append("&");
		}
		
		String str = sb.toString();
		if (str.endsWith("&")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	//对象转换成字符串
	public static String objectToString(Object value) {
		if (value == null) return "";
		if (value instanceof java.util.Date) {
			Date date = (Date) value;
			return formate(date, DATE_FORMATE);
		} else if (value instanceof java.sql.Date) {
			java.sql.Date date = (java.sql.Date) value;
			return formate(date, DATE_FORMATE);
		} else if (value instanceof BigDecimal) {
			BigDecimal bg = (BigDecimal) value;
			
			return bg.toString();
		}
		return value.toString();
	}
	
	public static Map<String, Object> beanToMap(Object order) {
		Map<String, Object> map = new TreeMap<String, Object>();
		if (order == null) return map;
		List<PropertyDescriptor> pdList = readPropertyDescriptors(order.getClass());
		for (PropertyDescriptor pd : pdList) {
			Method m1 = pd.getReadMethod();
			try {
				Object value = m1.invoke(order, null);
				map.put(pd.getName(), value);
			} catch (Exception e) {
				throw new RuntimeException("反射异常");
			} 
			
		}
		return map;
	}

	private static PropertyDescriptor[] propertyDescriptors(Class<?> c) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(c);
		} catch (IntrospectionException e) {
			throw new RuntimeException("Bean introspection failed: "+ e.getMessage());
		}

		return beanInfo.getPropertyDescriptors();
	}
	
	private static List<PropertyDescriptor> readPropertyDescriptors(Class<?> c) {
		PropertyDescriptor[] pds = propertyDescriptors(c);
		if (pds == null || pds.length < 1) return new ArrayList<PropertyDescriptor>(0);
		List<PropertyDescriptor> pdList = new ArrayList<PropertyDescriptor>(pds.length);
		for (PropertyDescriptor pd : pds) {
			Method readMethod = pd.getReadMethod();
			if (readMethod != null && !pd.getName().equals("class")) {
				pdList.add(pd);
			}
		}
		return pdList;
	}
	
	private static String formate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	private static String formate(java.sql.Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
}
