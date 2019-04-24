package com.kam.tomcat;



import com.kam.config.Config;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class MySpringApplication {

	public static void run() {
		//spring初始化
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(Config.class);
		appContext.refresh();

		File base = new File(System.getProperty("java.io.tmpdir"));
		//tomcat启动
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		Context context = tomcat.addContext("/", base.getAbsolutePath());

		DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
		Wrapper wrapper = tomcat.addServlet("/", "dispatcherServlet", dispatcherServlet);
		wrapper.setLoadOnStartup(1);
		wrapper.addMapping("/");

		try {
			tomcat.start();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}


	}
}
