package com.gomeo2o.jmt;

import io.terminus.ecp.user.model.User;
import io.terminus.pampas.common.Response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.gome.user.service.GomeUserWriteService;

public class JmeterUserRegister extends AbstractJavaSamplerClient {
	private static String label_name = "UserRegister";
	private static final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "spring-context-dubbo.xml" }); 
	private static GomeUserWriteService gomeUserWriteService = null;
	private static final Logger log = Logger.getLogger(JmeterUserRegister.class);
	@Override
	public void setupTest(JavaSamplerContext context) {
		gomeUserWriteService = (GomeUserWriteService) ctx.getBean("gomeUserWriteService");
	}

	@Override
	public void teardownTest(JavaSamplerContext context) {
		super.teardownTest(context);
	}

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();       
        params.addArgument("email", "em-");
        params.addArgument("nickname", "nn-");
        return params;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		boolean success = true;
        SampleResult sr = new SampleResult();
        sr.setSampleLabel(label_name);
        sr.sampleStart();//用来统计执行时间--start--
        String nickname = "";
        try {
        	User user = new User();
        	Map<String,Object> map = new HashMap<String,Object>();
			long seed = System.nanoTime();
			Random random = new Random(seed);
			String email = arg0.getParameter("email")+seed+"-" + random.nextInt(10) + "@163.com";
			nickname = arg0.getParameter("nickname")+seed+"-" + random.nextInt(10);
			user.setEmail(email);
			user.setPasswd(""+seed);
			user.setNickname(nickname);
			user.setType(1);       //买家
	        user.setStatus(0);   //未激活
			
			Map<String, Serializable> context = new HashMap<String, Serializable>();
			
			Response<Long> result = gomeUserWriteService.register(user, context);
			
			log.info(result.isSuccess() + "-" + result.getError());
			
            //Thread.sleep(5000);
            sr.setResponseMessage(result.getError());
            sr.setResponseCode(""+result.getResult());
        } catch (Exception e) {
        	log.error("runningError-"+nickname, e);
        	e.printStackTrace();
            success = false;
        }finally{
            sr.sampleEnd();//用来统计执行时间--end--
            sr.setSuccessful(success);
        }
        return sr;
	}

	public static void main(String[] args) {
//		User user = new User();
//        user.setEmail(email);
//        user.setPasswd(password);
//        user.setType(1);       //买家
//        user.setStatus(0);   //未激活
//        user.setNickname(nickname);
//
//        Response<Long> result = gomeUserWriteService.register(user, context);
	}
}
