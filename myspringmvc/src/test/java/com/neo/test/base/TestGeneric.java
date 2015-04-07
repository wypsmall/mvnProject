package com.neo.test.base;

public class TestGeneric {

	
	public static void main(String[] args) {
		print(new Integer(56));
	}
	
	private static <T> T print(T obj) {
		System.out.println(obj.toString());
		return obj;
	}
}
