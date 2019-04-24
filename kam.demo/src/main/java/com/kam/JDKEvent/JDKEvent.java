package com.kam.JDKEvent;

import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;

public class JDKEvent extends Observable {

	public void trigger() {
		setChanged();
		notifyObservers();
	}

}
