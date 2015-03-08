package com.neo.test.stproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloWorldHandler implements InvocationHandler {
	//要代理的原始对象
	private Object obj;
	
	
	public HelloWorldHandler(Object obj) {
		super();
		this.obj = obj;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		doBefore();
		result = method.invoke(obj, args);
		doAfter();
		return result;
	}

	private void doAfter() {
		System.out.println("jdk-proxy after method invoke");
		
	}


	private void doBefore() {
		System.out.println("jdk-proxy before method invoke");
	}

}
