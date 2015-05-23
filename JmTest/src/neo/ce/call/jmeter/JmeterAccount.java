package neo.ce.call.jmeter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import neo.ce.call.java.AccountClient;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gomeo2o.common.entity.CommonResultEntity;
import com.gomeo2o.facade.account.service.BudgetTotalFacade;

public class JmeterAccount extends AbstractJavaSamplerClient {
	private static String label_name = "dubbo_consumer";
	private static final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "spring-context-dubbo.xml" }); 
	private static BudgetTotalFacade budgetTotalFacade = null;
	private static final Logger log = Logger.getLogger(AccountClient.class);
	@Override
	public void setupTest(JavaSamplerContext context) {
		budgetTotalFacade = (BudgetTotalFacade) ctx.getBean("budgetTotalFacade");
	}

	@Override
	public void teardownTest(JavaSamplerContext context) {
		super.teardownTest(context);
	}

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();       
        params.addArgument("recordValue", "10");
        params.addArgument("budgetNo", "12");
        params.addArgument("userId", "1");
        params.addArgument("orderNo", "D-");
        params.addArgument("cardId", "121");
        params.addArgument("budgetType", "1");
        params.addArgument("state", "1");
        return params;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		boolean success = true;
        SampleResult sr = new SampleResult();
        sr.setSampleLabel(label_name);
        sr.sampleStart();//用来统计执行时间--start--
        String orderNo = "";
        try {
        	Map<String,Object> map = new HashMap<String,Object>();
			map.put("recordValue", Integer.valueOf(arg0.getParameter("recordValue")));
			map.put("budgetNo", Integer.valueOf(arg0.getParameter("budgetNo")));
			//预算流水部分信息
			map.put("userId", Integer.valueOf(arg0.getParameter("userId")));
			//map.put("detailNo", Constants.getRebateDetailNo());
			long seed = System.currentTimeMillis();
			Random random = new Random(seed);
			orderNo = arg0.getParameter("orderNo")+seed+"-" + random.nextInt(10);
			map.put("orderNo", orderNo);
			map.put("cardId", Integer.valueOf(arg0.getParameter("cardId")));
			map.put("budgetTime", new Date());
			map.put("budgetType", Integer.valueOf(arg0.getParameter("budgetType")));
			map.put("state", Integer.valueOf(arg0.getParameter("state")));
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list.add(map);
			log.info("=>" + map);
			CommonResultEntity<String> res = budgetTotalFacade.updateCutBudgetInfo(list);
			//System.out.println(res.getCode() + "-" + res.getMessage());
			log.info(res.getCode() + "-" + res.getMessage());
			
            //Thread.sleep(5000);
            sr.setResponseMessage(res.getMessage());
            sr.setResponseCode(""+res.getCode());
        } catch (Exception e) {
        	log.error("runningError-"+orderNo, e);
        	e.printStackTrace();
            success = false;
        }finally{
            sr.sampleEnd();//用来统计执行时间--end--
            sr.setSuccessful(success);
        }
        return sr;
	}

}
