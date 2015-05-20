package neo.ce;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BootStart {

	public static void main(String[] args) {
		try {
			CountDownLatch latch = new CountDownLatch(1);
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring/spring-context.xml" });
			//spring容器加载
			context.start();
			
			//添加钩子，推出时释放
			Runtime.getRuntime().addShutdownHook(new Thread() {
	            public void run() {
	            	context.close();
	            }
	        });
			
			//阻塞主进程
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
