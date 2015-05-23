package neo.ce;

import java.util.concurrent.CountDownLatch;

import neo.ce.service.IVirtualService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 * 
 */
public class VirtualClient {
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(1);
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-context-dubbo.xml" });
			context.start();
			for (int i = 0; i < 1; i++) {
				
				IVirtualService virtualService = (IVirtualService) context.getBean("virtualService");
				
				System.out.println(virtualService.hello("neo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			//阻塞主进程
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
