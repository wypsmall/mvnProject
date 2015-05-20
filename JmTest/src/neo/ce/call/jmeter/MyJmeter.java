package neo.ce.call.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class MyJmeter extends AbstractJavaSamplerClient {

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();       
        params.addArgument("name", "Tom");
        return params;
	}

	@Override
	public void setupTest(JavaSamplerContext context) {
		super.setupTest(context);
	}

	@Override
	public void teardownTest(JavaSamplerContext context) {
		super.teardownTest(context);
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		boolean success = true;
        SampleResult sr = new SampleResult();
        sr.setSampleLabel("MyJmeter");
        sr.sampleStart();//用来统计执行时间--start--
        try {
            String name = arg0.getParameter("name");
            Thread.sleep(5000);
            System.out.println(name);
            sr.setResponseMessage(name);
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
