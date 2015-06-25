package neo.ce;

import neo.ce.service.HelloWorld;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class WSClient {

	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
        factory.setAddress("http://localhost:8080/HelloWorld");  
        factory.setServiceClass(HelloWorld.class);  
        HelloWorld helloWorld = (HelloWorld) factory.create();  
        System.out.println(helloWorld.sayHello("zhuwei")); 
	}

}
