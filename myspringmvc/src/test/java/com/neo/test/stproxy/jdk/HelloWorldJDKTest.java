package com.neo.test.stproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.neo.test.stproxy.HelloWorld;
import com.neo.test.stproxy.HelloWorldImpl;

public class HelloWorldJDKTest {
	public static void main(String[] args) {
		HelloWorld helloWorld=new HelloWorldImpl();
        InvocationHandler handler=new HelloWorldHandler(helloWorld);
        
        //创建动态代理对象
        HelloWorld proxy=(HelloWorld)Proxy.newProxyInstance(
                helloWorld.getClass().getClassLoader(), 
                helloWorld.getClass().getInterfaces(), 
                handler);
        proxy.sayHelloWorld();
	}
}
