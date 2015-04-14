package com.neo.solr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * @Description: TODO
 * @author: wangyunpeng
 * @date: 2015年4月14日下午4:44:47
 */
public class SolrSearch {

	/**
	 * @Description:　TODO
	 * @author: wangyunpeng
	 * @date: 2015年4月14日下午4:44:47
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * http://10.144.52.195:8080/solr/gome/select?q=*%3A*&wt=json&indent=true
		 */
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("q", "subject:山");
			map.put("wt", "json");
			map.put("indent", "true");
			String content = HttpUtil.getRequestContent(map);
			String url_str = "http://10.144.52.195:8080/solr/gome/select";
			String res = HttpUtil.doHttpPost(content, HttpUtil.CONTENTTYPE_WWWFROM, HttpUtil.CHARSET_UTF8, url_str);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
