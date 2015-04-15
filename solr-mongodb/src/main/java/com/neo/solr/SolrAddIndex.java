package com.neo.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class SolrAddIndex {

	public static void main(String[] args) {
		try {
			String url = "http://10.144.52.195:8080/solr/goods";
			SolrServer server = new HttpSolrServer(url);
			String[] names = new String[]{"空调", "彩电", "冰箱", "洗衣机", "热水器", "家庭影院",
					"笔记本", "平板电脑", "台式机", "摄影摄像", "打印扫描",
					"潮流服装", "时尚鞋靴", "礼品箱包", "创意", "户外装备",
					"华为", "三星", "苹果", "中兴", "HTC", "魅族", "酷派" };
			
			List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			for (int i = 0; i < 15; i++) {
				
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", "id00002" + i);
				doc.addField("goodsid", "D000000001" + i);
				doc.addField("shopid", "SI0000"+i);
				doc.addField("name", "戴尔笔记本" + names[i+2]);
				doc.addField("title", "戴尔笔记本，商务系列" + names[i]);
				doc.addField("price", i+150.5F);
				docs.add(doc);
			}
			server.add(docs);
			server.commit();
			//server.optimize(); 
		} catch (Exception e) {
			System.out.println("请检查tomcat服务器或端口是否开启!");
			e.printStackTrace();
		}

	}

}
