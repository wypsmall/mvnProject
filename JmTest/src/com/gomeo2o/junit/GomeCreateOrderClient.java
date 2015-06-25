package com.gomeo2o.junit;

import io.terminus.ecp.user.dto.EcpLoginUser;
import io.terminus.ecp.user.dto.LoginUser;
import io.terminus.pampas.common.Response;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.gome.trade.service.GomeOrderWriteService;

/**
 * Hello world!
 * 
 */
public class GomeCreateOrderClient {
	private static final Logger log = Logger.getLogger(GomeCreateOrderClient.class);
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-context-dubbo.xml" });
			context.start();
			GomeOrderWriteService gomeOrderWriteService = (GomeOrderWriteService) context.getBean("gomeOrderWriteService");

			//tradeInfoJson
			String tradeInfoJson = getTradeInfoJson();
			//addressId
			Integer addressId = getAddressId();
			//channel
			String channel = getChannel();
			//dataJson
			String dataJson = getDataJson();
			//user
			LoginUser user = getUser();
			
			Response<Map<String, Serializable>> result = gomeOrderWriteService.gomeCreateOrder(tradeInfoJson, addressId, channel, dataJson, user);
			Map<String, Serializable> rdata = result.getResult();
			log.info(result.isSuccess() + "-" + result.getError() + "-" + (null==rdata?null:rdata.toString()));
			System.out.println("========>"+result.toString());
			//Response(success=true, result={orderIds=[386]}, error=null)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static LoginUser getUser() {
		//EcpLoginUser(id=12345763, type=1, status=1, nickname=null, email=null, mobile=null)
		EcpLoginUser user=new EcpLoginUser();
		user.setId(12345763L);
		user.setType(1);
		user.setStatus(1);
		return user;
	}
	private static String getDataJson() {
/*		String data="[{\"remark\":\"1#true#\",\"invoice\":\"{\"title\":\"*公司\",\"type\":1}\","
				+ "\"sellerId\":3,\"marketingParams\":null,"
				+ "\"skuIdAndInfos\":[{\"getOutParams\":{\"kId\":0},\"skuId\":1,\"quantity\":1,\"itemExtra\":null,\"referenceId\":\"0#1\",\"reference\":\"微店ID：1   kId:{kId=0}\"}],"
				+ "\"isGome\":0,"
				+ "\"payType\":1,"
				+ "\"buyerNotes\":\"\","
				+ "\"needSettle\":1}]";  */ 
		//在线商品
		//String data = "[{\"remark\":\"1#true#无\",\"invoice\":\"{\\\"title\\\":\\\"*公司\\\",\\\"type\\\":1}\",\"sellerId\":3,\"marketingParams\":null,\"skuIdAndInfos\":[{\"getOutParams\":{\"kId\":888},\"skuId\":136,\"quantity\":1,\"itemExtra\":null,\"referenceId\":\"888#7\",\"reference\":\"微店ID：7   kId:{kId=888}\"}],\"isGome\":1,\"payType\":1,\"buyerNotes\":\"\",\"needSettle\":1}]";
		String data = "[{\"remark\":\"1#true#\",\"invoice\":\"{\\\"title\\\":\\\"*公司\\\",\\\"type\\\":1}\",\"sellerId\":3,\"marketingParams\":null,\"skuIdAndInfos\":[{\"getOutParams\":{\"kId\":0},\"skuId\":1,\"quantity\":1,\"itemExtra\":null,\"referenceId\":\"0#1\",\"reference\":\"微店ID：1   kId:{kId=0}\"}],\"isGome\":0,\"payType\":1,\"buyerNotes\":\"\",\"needSettle\":1}]";
		return data;
	}
	private static String getChannel() {
		return "alipay";
	}
	private static Integer getAddressId() {
		return 110103001;
	}
	private static String getTradeInfoJson() {
/*		String tradeInfo="{\"provinceId\":72000000,"
				+ "\"region\":\"华坪县\","
				+ "\"zip\":\"\","
				+ "\"phone\":\"\","
				+ "\"cityId\":72080000,"
				+ "\"status\":1,"
				+ "\"street\":\"兴泉镇\","
				+ "\"city\":\"丽江市\","
				+ "\"id\":112233,"
				+ "\"updatedAt\":\"2015-06-19 15:44:49\","
				+ "\"details\":\"啊实打实大\","
				+ "\"isDefault\":0,"
				+ "\"streetId\":720804006,"
				+ "\"createdAt\":\"2015-06-19 15:44:49\","
				+ "\"userId\":12345705,"
				+ "\"name\":\"啊实打实的\","
				+ "\"province\":\"云南省\","
				+ "\"mobile\":\"18612343234\","
				+ "\"regionId\":72080400}";*/
		//在线商品
		//String tradeInfo = "{\"provinceId\":11000000,\"region\":\"丰台区(五环里)\",\"zip\":null,\"phone\":null,\"cityId\":11010000,\"status\":1,\"street\":\"全部区域\",\"city\":\"北京市\",\"id\":17,\"updatedAt\":\"2015-06-17 10:04:38\",\"details\":\"华山派思过崖\",\"isDefault\":1,\"streetId\":110103001,\"createdAt\":\"2015-06-16 16:26:18\",\"userId\":12345763,\"name\":\"令狐冲\",\"province\":\"北京\",\"mobile\":\"13811664749\",\"regionId\":11010300}";
		String tradeInfo = "{\"provinceId\":11000000,\"region\":\"丰台区(五环里)\",\"zip\":null,\"phone\":null,\"cityId\":11010000,\"status\":1,\"street\":\"全部区域\",\"city\":\"北京市\",\"id\":17,\"updatedAt\":\"2015-06-17 10:04:38\",\"details\":\"华山派思过崖\",\"isDefault\":1,\"streetId\":110103001,\"createdAt\":\"2015-06-16 16:26:18\",\"userId\":12345763,\"name\":\"令狐冲\",\"province\":\"北京\",\"mobile\":\"13811664749\",\"regionId\":11010300}";
		return tradeInfo;
	}
}
