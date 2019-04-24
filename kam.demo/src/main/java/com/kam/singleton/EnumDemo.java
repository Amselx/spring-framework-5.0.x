package com.kam.singleton;

class EnumDemo {

	private enum EnumSingleton {
		EnumDemo;
		EnumDemo instance = new EnumDemo();
	}

	private EnumDemo(){}

	static EnumDemo getInstance() {
		return EnumSingleton.EnumDemo.instance;
	}

}
