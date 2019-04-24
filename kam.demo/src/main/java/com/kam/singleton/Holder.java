package com.kam.singleton;


/**
 * Holder单例
 */
public class Holder {

	private static class HolderSingleton {
		private static Holder holder = new Holder();
	}

	private Holder() { }

	public static Holder getInstance() {
		return HolderSingleton.holder;
	}
}


