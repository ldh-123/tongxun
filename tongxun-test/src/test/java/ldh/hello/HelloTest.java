package ldh.hello;

import ldh.test.Hello;
import ldh.tongxun.json.PathRegister;
import base.BaseSpringTest;

public class HelloTest {

//	private Hello hello = BaseSpringTest.getBean(Hello.class);
//	private PathRegister pathRegister = BaseSpringTest.getBean(PathRegister.class);
//	
	public void test() {
//		System.out.println(pathRegister);
//		hello.hello("adfas");
//		System.out.println(pathRegister);
		
		System.out.println(BaseSpringTest.getBean("md5Security"));
	}
	
	public static void main(String[] args) {
		HelloTest ht = new HelloTest();
		ht.test();
		System.out.println("sdafaf");
	}

}
