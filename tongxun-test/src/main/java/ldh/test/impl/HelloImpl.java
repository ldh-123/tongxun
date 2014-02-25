package ldh.test.impl;

import java.util.ArrayList;
import java.util.List;

import ldh.test.Hello;
import ldh.test.HelloPojo;
import ldh.tongxun.json.ParamKey;

public class HelloImpl implements Hello {

	@Override
	public String hello(String name) {
		System.out.println("hello:" + name);
		
		return "hello " + name;
	}

	@Override
	public void test() {
		System.out.println("test=======================");
		
	}

	@Override
	public void test1(String name) {
		System.out.println("test1=======================:" + name);
		
	}

	@Override
	public List<String> test2(String name) {
		List<String> ts = new ArrayList<String>();
		for (int i=0; i<10; i++) {
			ts.add(name + "\t" + i);
		}
		return ts;
	}

	@Override
	public List<String> test2(List<String> names) {
		List<String> ts = new ArrayList<String>();
		if (names != null) {
			for (String n : names) {
				ts.add("test2\t" + n);
			}
		}
		return ts;
	}
	
	public List<String> test3(@ParamKey("name")HelloPojo name) {
		List<String> ts = new ArrayList<String>();
		if (name != null) {
			ts.add(name.getName());
			ts.add(name.getAge()+"");
		}
		return ts;
	}
	
	public Float test4(@ParamKey("age")int age) {
		return new Float(age * 1.23);
	}

	public Double test5(@ParamKey("age")int age) {
		return new Double(age * 2.34d);
	}
}
