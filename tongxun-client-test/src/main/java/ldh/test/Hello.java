package ldh.test;

import java.util.List;

import ldh.tongxun.json.ParamKey;

public interface Hello {

	public String hello(@ParamKey("name")String name);
	
	public void test();
	
	public void test1(@ParamKey("name")String name);
	
	public List<String> test2(@ParamKey("name")String name);
	
	public List<String> test2(@ParamKey("name")List<String> name);
	
	public List<String> test3(@ParamKey("name")HelloPojo name);
	
	public Float test4(@ParamKey("age")int age);
	
	public Double test5(@ParamKey("age")int age);
}
