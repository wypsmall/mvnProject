package neo.ce;

import javax.xml.ws.Endpoint;

import neo.ce.service.impl.HelloWorldImpl;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class MainServer {

	public static void main(String[] args) {
		// 第一种发布方式：通过CXF提供的JaxWsServerFactoryBean来发布webservice
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		factory.setServiceClass(HelloWorldImpl.class);
		factory.setAddress("http://localhost:8080/HelloWorld");

		Server server = factory.create();
		server.start();

		// 第二种方式，通过JAX-WS提供的Endpoint来发布webservice
		// 首先创建webservice服务提供类的实例
/*		HelloWorldImpl implementor = new HelloWorldImpl();
		String address = "http://localhost:8080/HelloWorld";
		Endpoint.publish(address, implementor);*/

	}
}
