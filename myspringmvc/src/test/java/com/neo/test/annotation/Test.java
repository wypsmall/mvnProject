package com.neo.test.annotation;

public class Test {
	public static void main(String[] args){
        User user=new User("张三", "xdemo.org", "252878950@qq.com");
        try {
            ValidateService.valid(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user=new User("zhangsan","xdemo.org","xxx@");
        try {
            ValidateService.valid(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user=new User("zhangsan","xdemo.org","");
        try {
            ValidateService.valid(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
