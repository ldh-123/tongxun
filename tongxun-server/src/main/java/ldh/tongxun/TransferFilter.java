package ldh.tongxun;

import java.lang.reflect.Method;

public interface TransferFilter {

	public Object before(Method method, Object[] args);
	
	public String after(String json);
}
