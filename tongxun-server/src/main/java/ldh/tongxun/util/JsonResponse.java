package ldh.tongxun.util;

import java.util.List;

public class JsonResponse<T> {

	private boolean isSuccess;
	
	private String info;
	
	private T bean;
	
	private List<T> beans;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	public List<T> getBeans() {
		return beans;
	}

	public void setBeans(List<T> beans) {
		this.beans = beans;
	}
	
	public boolean isList() {
		return beans != null && bean == null;
	}
	
	
}
