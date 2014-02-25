package ldh.tongxun.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldh.tongxun.util.JsonView.JsonViewFactory;

public class TongxunServlet extends HttpServlet {

	 public void doGet(HttpServletRequest request,
			  HttpServletResponse response)throws IOException,ServletException{
		 doPost(request, response);
	 }
	 
	 public void doPost(HttpServletRequest request,
			  HttpServletResponse response)throws IOException,ServletException{
		 Map<String, String[]> data = request.getParameterMap();
		 Map<String, Object> dd = new HashMap<String, Object>(data.size());
		 for (Entry<String, String[]> entry : data.entrySet()) {
			 dd.put(entry.getKey(), entry.getValue()[0]);
		 }
		 try {
			 String d = MessageHandle.handle(dd);
			 sendMessage(response, d);
		 } catch(Exception e) {
			 error(response, e.getMessage());
			 return;
		 }
	 }

	 private void error(HttpServletResponse response, String msg) throws IOException {
		 String json = JsonViewFactory.create().info(msg).success(false).toJson();
		 json(response, json); 
	 }
	 
	 private void sendMessage(HttpServletResponse response, String d) throws IOException {
		json(response, d);
	 }
	 
	 private void json(HttpServletResponse response, String msg) throws IOException {
		 response.setContentType("application/json;charset=UTF-8");  
		 response.setHeader("Cache-Control","no-cache"); 
		 
		 response.getWriter().write(msg);
		 response.getWriter().flush();
	 }
	 
	 
}
