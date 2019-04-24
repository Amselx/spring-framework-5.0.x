package com.kam.singleton;

public class test {

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(()-> {

					System.out.println(EnumDemo.getInstance());

			}).start();
		}
	}
}
