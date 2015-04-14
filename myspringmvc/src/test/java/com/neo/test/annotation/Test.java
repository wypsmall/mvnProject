package com.neo.test.annotation;

public class Test {
	public static void main(String[] args) {

		Thread th1 = new Thread(new Runnable() {

			@Override
			public void run() {
				User user = new User("张三12345678", "1234567", "252878950@123.com");
				try {
					ValidateService.valid(user);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		Thread th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				User user = new User("zhangsan1234567", "12345678", "xxx@123.com");
				try {
					ValidateService.valid(user);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		
		th1.start();
		th2.start();
		/*
		 * user=new User("zhangsan","xdemo.org","xxx@"); try {
		 * ValidateService.valid(user); } catch (Exception e) {
		 * e.printStackTrace(); } user=new User("zhangsan","xdemo.org",""); try
		 * { ValidateService.valid(user); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
	}
}
