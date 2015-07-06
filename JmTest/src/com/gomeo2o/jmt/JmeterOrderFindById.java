package com.gomeo2o.jmt;

import io.terminus.ecp.trade.model.Order;
import io.terminus.pampas.common.Response;

import java.util.Random;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.gome.trade.service.GomeOrderReadService;

public class JmeterOrderFindById extends AbstractJavaSamplerClient {
	private static String label_name = "JmeterItemQuery";
	private static final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "spring-context-dubbo.xml" }); 
	private static GomeOrderReadService gomeOrderReadService = null;
	private static final Logger log = Logger.getLogger(JmeterOrderFindById.class);
	@Override
	public void setupTest(JavaSamplerContext context) {
		gomeOrderReadService = (GomeOrderReadService) ctx.getBean("gomeOrderReadService");
	}

	@Override
	public void teardownTest(JavaSamplerContext context) {
		super.teardownTest(context);
	}

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();       
        params.addArgument("OrderId", "101");
        return params;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		boolean success = true;
        SampleResult sr = new SampleResult();
        sr.setSampleLabel(label_name);
        sr.sampleStart();//用来统计执行时间--start--
        try {
        	String strId = arg0.getParameter("OrderId");
        	Long orderId = 0L;
        	if(null == strId || "".equals(strId) || "0".equals(strId)) {
        		long seed = System.nanoTime();
    			Random random = new Random(seed);
    			orderId = (long) random.nextInt(100000);
        	} else {
        		orderId = Long.valueOf(strId);
        	}
        	System.out.println("========>OrderId:"+strId);
        	Response<Order> result = gomeOrderReadService.findOrderById(orderId);
			Order order = result.getResult();
			log.info(result.isSuccess() + "-" + result.getError() + "-" + (null==order?null:order.toString()));
			System.out.println("========>"+result.toString());
            //Thread.sleep(5000);
            sr.setResponseMessage(result.getError());
            sr.setResponseCode(""+result.getResult());
        } catch (Exception e) {
        	log.error("runTest-", e);
        	e.printStackTrace();
            success = false;
        }finally{
            sr.sampleEnd();//用来统计执行时间--end--
            sr.setSuccessful(success);
        }
        return sr;
	}


}
