package neo.ce.jmeter;

import neo.ce.service.IVirtualService;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 过期，参见JmTest project 
 * @author: wangyunpeng
 * @date: 2015年5月21日上午9:22:42
 */
public class JmeterVirtual extends AbstractJavaSamplerClient {
	private static String label_name = "dubbo_consumer";
	private static final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring-context-dubbo.xml"); 
	private static IVirtualService virtualService = null;
	
	
	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();       
        params.addArgument("name", "Tom");
        return params;
	}


	@Override
	public void setupTest(JavaSamplerContext context) {
		virtualService = (IVirtualService) ctx.getBean("virtualService");
	}


	@Override
	public void teardownTest(JavaSamplerContext context) {
		super.teardownTest(context);
	}


	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		boolean success = true;
        SampleResult sr = new SampleResult();
        sr.setSampleLabel(label_name);
        sr.sampleStart();//用来统计执行时间--start--
        try {
            String name = arg0.getParameter("name");
            String msg = virtualService.hello(name);
            Thread.sleep(5000);
            System.out.println(msg);
            sr.setResponseMessage(msg);
            sr.setResponseCode("1000");
        } catch (Exception e) {
            success = false;
        }finally{
            sr.sampleEnd();//用来统计执行时间--end--
            sr.setSuccessful(success);
        }
        return sr;
	}

}
