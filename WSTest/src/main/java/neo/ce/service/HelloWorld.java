package neo.ce.service;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
	public String sayHello(String name); 
}
