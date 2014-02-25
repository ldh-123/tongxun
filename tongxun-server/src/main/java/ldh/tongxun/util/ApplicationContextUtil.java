package ldh.tongxun.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	public static ApplicationContext getContext(){
		return context;
	}

	@Override
	public void setApplicationContext(
			org.springframework.context.ApplicationContext contex)
			throws BeansException {
		this.context = contex;
	}
	
	public static <T> T getBean(String beanName) {
		return (T) context.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return (T) context.getBean(clazz);
	}
}
