package com.kam.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TriggerListener  {

	@Autowired
	private ApplicationContext applicationContext;


	public void trigger() {
		applicationContext.publishEvent(new MySpringEvent(applicationContext));
	}

}
