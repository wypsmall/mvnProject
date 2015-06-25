package neo.ce.service.impl;

import javax.jws.WebService;

import neo.ce.service.HelloWorld;

@WebService
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String sayHello(String name) {
		System.out.println("sayHello方法被调用");  
        return ("Hello"+name);  
	}

}
