package com.gomeo2o.jmt;

import io.terminus.ecp.item.dto.ItemInfo;
import io.terminus.pampas.common.Response;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.gome.item.service.GomeItemReadService;

public class JmeterItemQuery extends AbstractJavaSamplerClient {
	private static String label_name = "JmeterItemQuery";
	private static final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "spring-context-dubbo.xml" }); 
	private static GomeItemReadService itemReadService = null;
	private static final Logger log = Logger.getLogger(JmeterItemQuery.class);
	@Override
	public void setupTest(JavaSamplerContext context) {
		itemReadService = (GomeItemReadService) ctx.getBean("itemReadService");
	}

	@Override
	public void teardownTest(JavaSamplerContext context) {
		super.teardownTest(context);
	}

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();       
        params.addArgument("ItemId", "101");
        return params;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		boolean success = true;
        SampleResult sr = new SampleResult();
        sr.setSampleLabel(label_name);
        sr.sampleStart();//用来统计执行时间--start--
        try {
        	String strId = arg0.getParameter("ItemId");
        	Long itemId = 0L;
        	if(null == strId || "".equals(strId) || "0".equals(strId)) {
        		long seed = System.nanoTime();
    			Random random = new Random(seed);
    			itemId = (long) random.nextInt(22230);
        	} else {
        		itemId = Long.valueOf(strId);
        	}
        	System.out.println("========>item:"+itemId);
        	Set<ItemInfo.Menu> paramSet = new HashSet<ItemInfo.Menu>();
			paramSet.add(ItemInfo.Menu.BASIC);
			Response<ItemInfo> result = itemReadService.getItemInfoDetail(itemId, paramSet);
			ItemInfo itemInfo = result.getResult();
			log.info(result.isSuccess() + "-" + result.getError() + "-" + (null==itemInfo?null:itemInfo.toString()));
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
