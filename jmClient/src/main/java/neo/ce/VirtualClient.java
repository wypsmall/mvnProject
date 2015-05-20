package neo.ce;

import neo.ce.service.IVirtualService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 * 
 */
public class VirtualClient {
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-context-dubbo.xml" });
			context.start();
			IVirtualService virtualService = (IVirtualService) context.getBean("virtualService");

			System.out.println(virtualService.hello("neo"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
