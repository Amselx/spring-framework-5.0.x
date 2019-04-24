package com.kam.JDKEvent;

import java.util.Observable;
import java.util.Observer;

public class JDKListener implements Observer {
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("go want wc");
	}
}
