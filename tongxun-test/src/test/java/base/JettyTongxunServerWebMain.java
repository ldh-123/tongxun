/*
// * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author lxf
 */
public class JettyTongxunServerWebMain {

    private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
    private static Server jettyServer;

    private static Server createServerInSource(int port, String contextPath) {
        Server server = new Server();
        // 设置在JVM�?��时关闭Jetty的钩子�?
        server.setStopAtShutdown(true);

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);

        // 解决Windows下重复启动Jetty居然不报端口冲突的问�?
        connector.setReuseAddress(false);

        server.setConnectors(new Connector[]{connector});

//        ServerConnector connector = new ServerConnector(server);
//        connector.setPort(port);
//        connector.setReuseAddress(false);
        
        WebAppContext webContext = new WebAppContext(DEFAULT_WEBAPP_PATH, contextPath);
        webContext.setDisplayName(contextPath);
//        webContext.setResourceBase(contextPath);
        // 设置使用当前线程的ClassLoder以解决JSP不能正确扫描taglib的tld文件的问题�?
        webContext.setClassLoader(Thread.currentThread()
                .getContextClassLoader());
        
        server.setHandler(webContext);

        return server;
    }
    
    public static void main(String[] args) throws Exception {
    	Server server = createServerInSource(8080, "/");
    	server.start();
    	server.join();
    }
}
