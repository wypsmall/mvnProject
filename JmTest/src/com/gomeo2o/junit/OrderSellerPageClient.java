package com.gomeo2o.junit;

import io.terminus.common.model.Paging;
import io.terminus.ecp.trade.dto.RichOrderSellerView;
import io.terminus.ecp.user.dto.EcpLoginUser;
import io.terminus.pampas.common.Response;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.gome.trade.service.GomeOrderReadService;

/**
 * Hello world!
 * 
 */
public class OrderSellerPageClient {
	private static final Logger log = Logger.getLogger(OrderSellerPageClient.class);
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-context-dubbo.xml" });
			context.start();
			GomeOrderReadService gomeOrderReadService = (GomeOrderReadService) context.getBean("gomeOrderReadService");

			EcpLoginUser user=new EcpLoginUser();
			user.setId(12345763L);
			user.setType(1);
			user.setStatus(1);
			
			Long orderId = 101L;
			
			//sellerPagingOrderItem
			Response<Paging<RichOrderSellerView>> result = gomeOrderReadService.sellerPaging(user, 1, orderId, "", "", "", 1, 5, "", "");
			Paging<RichOrderSellerView> pageData = result.getResult();
			log.info(result.isSuccess() + "-" + result.getError() + "-" + (null==pageData?null:pageData.toString()));
			System.out.println("========>"+result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
