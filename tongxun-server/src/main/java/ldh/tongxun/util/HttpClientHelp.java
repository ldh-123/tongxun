package ldh.tongxun.util;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;


public class HttpClientHelp {

	private static final Logger log = Logger.getLogger(HttpClientHelp.class);
	
	private static String doPost(String baseUrl, Map<String, Object> params) {
		PostMethod postMethod = new PostMethod(baseUrl);

		if (params != null) {
			NameValuePair nvp;
	        for (Map.Entry<String, Object> entry : params.entrySet()) {
	        	String value = Util.objectToString(entry.getValue());
	            nvp = new NameValuePair(entry.getKey(), value);
	            postMethod.addParameter(nvp);
	        }
		}

        HttpClient httpClient = new HttpClient();
        int stateCode;
		try {
			stateCode = httpClient.executeMethod(postMethod);
			if (stateCode == HttpStatus.SC_OK) {
	            return postMethod.getResponseBodyAsString();
	        } 
		} catch (Exception e) {
			throw new RuntimeException("httpclient 调用异常", e);
		} 
        
        throw new RuntimeException("httpclient post 发生异常, code = " + stateCode);
        
	}
	
	private static String doGet(String baseUrl, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		boolean isfirst = true;
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
	        	String value = Util.objectToString(entry.getValue());
	        	if (!isfirst) {
	        		sb.append("&").append(entry.getKey()).append("=").append(value);
	        	} else {
	        		isfirst = false;
	        		sb.append(entry.getKey()).append("=").append(value);
	        	}
	            
	        }
		}
		
		if(sb.toString().length() > 0) {
			if (!baseUrl.contains("?")) {
				baseUrl += "?" + sb.toString();
			} else {
				baseUrl += "&" + sb.toString();
			}
		}
		GetMethod getMethod = new GetMethod(baseUrl);
		
        HttpClient httpClient = new HttpClient();
        int stateCode;
		try {
			stateCode = httpClient.executeMethod(getMethod);
			if (stateCode == HttpStatus.SC_OK) {
	            return getMethod.getResponseBodyAsString();
	        } 
		} catch (Exception e) {
			throw new RuntimeException("httpclient 调用异常", e);
		} 
        
        throw new RuntimeException("httpclient get 发生异常, code=" + stateCode);
	}
	
	public static <T> JsonResponse<T> post(String baseUrl, Map<String, Object> params, Class<T> clazz) {
		String response = doPost(baseUrl, params);
		log.debug("#orderQuery post response :" + response);
		return Util.toJsonResponse(response, clazz);
	}
	
	public static String post(String baseUrl, Map<String, Object> params) {
		String response = doPost(baseUrl, params);
		log.debug("#orderQuery post response :" + response);
		return response;
	}
	
	public static <T> JsonResponse<T> get(String baseUrl, Map<String, Object> params, Class<T> clazz) {
		String response = doGet(baseUrl, params);
		log.debug("#orderQuery get response :" + response);
		return Util.toJsonResponse(response, clazz);
	}
	
	
}
