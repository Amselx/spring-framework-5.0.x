package com.kam.singleton;

import java.sql.Time;

public class DLC {
	private DLC() {}

	private volatile static DLC dlc = null;

	public static DLC getInstance() throws InterruptedException {
		if (dlc == null) {

			synchronized (DLC.class) {
				if (dlc == null) {
					dlc = new DLC();
				}
			}
		}
		return dlc;
	}
}
