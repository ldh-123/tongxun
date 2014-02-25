package base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BaseSpringTest {

	protected final static String[] xmls = new String[] {
		"classpath:applicationContext.xml"
	};
	
	protected static ApplicationContext context = null;
	static {
		context = new GenericXmlApplicationContext(xmls);
	}
	
	public static <T>T getBean(Class<T> t) {
		return context.getBean(t);
	}
	
	public static <T>T getBean(String name) {
		return (T) context.getBean(name);
	}
}
