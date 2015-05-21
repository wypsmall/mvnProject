package neo.ce;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import neo.ce.service.IVirtualService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringScope {

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-test.xml" });
			//spring容器加载
			context.start();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
		    String s = br.readLine();
		    while (!"exit".equals(s)) {
		    	IVirtualService service = (IVirtualService)context.getBean("virtualService");
		    	System.out.println("=============>" + service.hello(s));
		    	s = br.readLine();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
