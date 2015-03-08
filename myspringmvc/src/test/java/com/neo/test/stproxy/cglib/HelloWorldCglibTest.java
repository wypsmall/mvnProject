package com.neo.test.stproxy.cglib;

import com.neo.test.stproxy.HelloWorldOrg;

public class HelloWorldCglibTest {

	public static void main(String[] args) {
		HelloWorldOrg helloWorld=new HelloWorldOrg();
        CglibProxy cglibProxy=new CglibProxy();
        HelloWorldOrg hw=(HelloWorldOrg)cglibProxy.createProxy(helloWorld);
        hw.sayHelloWorld();
	}

}
