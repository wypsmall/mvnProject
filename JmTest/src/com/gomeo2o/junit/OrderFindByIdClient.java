package com.gomeo2o.junit;

import io.terminus.ecp.trade.model.Order;
import io.terminus.pampas.common.Response;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.gome.trade.service.GomeOrderReadService;

/**
 * Hello world!
 * 
 */
public class OrderFindByIdClient {
	private static final Logger log = Logger.getLogger(OrderFindByIdClient.class);
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-context-dubbo.xml" });
			context.start();
			GomeOrderReadService gomeOrderReadService = (GomeOrderReadService) context.getBean("gomeOrderReadService");

			Long orderId = 101L;

			Response<Order> result = gomeOrderReadService.findOrderById(orderId);
			Order order = result.getResult();
			log.info(result.isSuccess() + "-" + result.getError() + "-" + (null==order?null:order.toString()));
			System.out.println("========>"+result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
