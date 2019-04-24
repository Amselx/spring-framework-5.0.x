package com.kam.Event;

import com.kam.Dao;
import com.kam.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		context.getBean(TriggerListener.class).trigger();
		Dao dao = context.getBean(Dao.class);
		dao.query();
	}
}
