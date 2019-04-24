package com.kam.Event;

import org.springframework.context.ApplicationEvent;

public class MySpringEvent extends ApplicationEvent {
	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public MySpringEvent(Object source) {
		super(source);
	}
}
