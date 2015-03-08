package com.neo.test.stproxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
	//要代理的原始对象
    private Object obj;
    
    public Object createProxy(Object target) {
        this.obj = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.obj.getClass());// 设置代理目标
        enhancer.setCallback(this);// 设置回调
        enhancer.setClassLoader(target.getClass().getClassLoader());
        return enhancer.create();
    }
    
	@Override
	public Object intercept(Object proxy, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
		Object result = null;
        // 调用之前
        doBefore();
        // 调用原始对象的方法
        result = methodProxy.invokeSuper(proxy, params);
        // 调用之后
        doAfter();
        return result;
	}

	private void doBefore() {
        System.out.println("cglib-proxy before method invoke");
    }

    private void doAfter() {
        System.out.println("cglib-proxy after method invoke");
    }
}
