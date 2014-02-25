package ldh.hello;

import java.util.Arrays;
import java.util.List;

import ldh.test.Hello;
import ldh.test.HelloPojo;
import base.BaseSpringTest;

public class HelloTest {

	private Hello hello = BaseSpringTest.getBean(Hello.class);
	
	public void test0() {
		String name = hello.hello("adfas");
		System.out.println("=================" + name);
	}
	
	public void test() {
		hello.test();
		System.out.println("=================test");
	}
	
	public void test1() {
		hello.test1("ldh");
		System.out.println("=================test1");
	}
	
	
	public void test2() {
		List<String> tt = hello.test2("ldh");
		if (tt != null) {
			for (String t : tt) {
				System.out.println("test3 : " + t);
			}
		}
		System.out.println("=================test2");
	}
	
	public void test21() {
		List<String> tt = hello.test2(Arrays.asList("ldh1", "ldh2", "ldh3", "ldh4"));
		if (tt != null) {
			for (String t : tt) {
				System.out.println("test31 : " + t);
			}
		}
		System.out.println("=================test21");
	}
	
	public void test3() {
		HelloPojo hp = new HelloPojo();
		hp.setName("ldh");
		hp.setAge(23);
		List<String> tt = hello.test3(hp);
		if (tt != null) {
			for (String t : tt) {
				System.out.println("test31 : " + t);
			}
		}
		System.out.println("=================test3");
	}
	
	public void test4() {
		float d = hello.test4(28);
		System.out.println("=================test4:" + d);
	}
	
	public void test5() {
		double d = hello.test5(28);
		System.out.println("=================test5:" + d);
	}
	
	public static void main(String[] args) {
		HelloTest ht = new HelloTest();
		ht.test();
		ht.test1();
		ht.test2();
		ht.test21();
		ht.test3();
		ht.test4();
		ht.test5();
		
	}

}
