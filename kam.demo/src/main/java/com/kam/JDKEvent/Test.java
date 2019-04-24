package com.kam.JDKEvent;

public class Test {
	public static void main(String[] args) {
		JDKEvent event = new JDKEvent();
		JDKListener listener = new JDKListener();
		event.addObserver(listener);
//		event.deleteObserver(listener);
//		event.deleteObservers();
		event.trigger();
	}
}
