package com.kam.Event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MySpringListener implements ApplicationListener<MySpringEvent> {
	@Override
	public void onApplicationEvent(MySpringEvent event) {
		System.out.println("执行监听事件");
		System.out.println(event);
	}
}
