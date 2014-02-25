package ldh.test.controller;

import java.util.ArrayList;
import java.util.List;

import ldh.tongxun.util.JsonView.JsonViewFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@RequestMapping(value="/test")
	@ResponseBody
	public String test(String name) {
		List<String> ts = new ArrayList<String>();
		for (int i=0; i<10; i++) {
			ts.add(name + "\t" + i);
		}
	
		return JsonViewFactory.create().success(true).put("data", ts).toJson();
	}
}
