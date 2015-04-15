package com.neo.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrSearchShopId {

	public static void main(String[] args) {
		try {
			String url = "http://10.144.52.195:8080/solr/goods";
			SolrServer server = new HttpSolrServer(url);
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("(shopid:SI00001 OR shopid:SI00006) AND name:**");
			QueryResponse rsp = server.query(solrQuery); 
			SolrDocumentList list = rsp.getResults();
			for (SolrDocument solrDocument : list) {
				System.out.println("id: " + solrDocument.toString());
			}
			
		} catch (Exception e) {
			System.out.println("请检查tomcat服务器或端口是否开启!");
			e.printStackTrace();
		}

	}

}
