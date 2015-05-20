package neo.ce.call.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gomeo2o.common.entity.CommonResultEntity;
import com.gomeo2o.facade.account.service.BudgetTotalFacade;

/**
 * Hello world!
 * 
 */
public class AccountClient {
	private static final Logger log = Logger.getLogger(AccountClient.class);
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-context-dubbo.xml" });
			context.start();
			BudgetTotalFacade budgetTotalFacade = (BudgetTotalFacade) context.getBean("budgetTotalFacade");

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("recordValue", 1);
			map.put("budgetNo", 12);
			//预算流水部分信息
			map.put("userId", 1);
			//map.put("detailNo", Constants.getRebateDetailNo());
			map.put("orderNo", 1);
			map.put("cardId", 1);
			map.put("budgetTime", new Date());
			map.put("budgetType", 1);
			map.put("state", 1);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list.add(map);
			CommonResultEntity<String> res = budgetTotalFacade.updateCutBudgetInfo(list);
			log.info(res.getCode() + "-" + res.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
