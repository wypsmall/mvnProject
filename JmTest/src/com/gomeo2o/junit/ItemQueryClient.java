package com.gomeo2o.junit;

import io.terminus.ecp.item.dto.ItemInfo;
import io.terminus.pampas.common.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.gome.item.service.GomeItemReadService;
import cn.com.gome.user.service.GomeUserWriteService;

import com.gomeo2o.common.entity.CommonResultEntity;
import com.gomeo2o.facade.account.service.BudgetTotalFacade;

/**
 * Hello world!
 * 
 */
public class ItemQueryClient {
	private static final Logger log = Logger.getLogger(ItemQueryClient.class);
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-context-dubbo.xml" });
			context.start();
			GomeItemReadService itemReadService = (GomeItemReadService) context.getBean("itemReadService");


			Set<ItemInfo.Menu> paramSet = new HashSet<ItemInfo.Menu>();
			paramSet.add(ItemInfo.Menu.BASIC);
			Long itemId = 101L;
			Response<ItemInfo> result = itemReadService.getItemInfoDetail(itemId, paramSet);
			ItemInfo itemInfo = result.getResult();
			log.info(result.isSuccess() + "-" + result.getError() + "-" + (null==itemInfo?null:itemInfo.toString()));
			System.out.println("========>"+result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
