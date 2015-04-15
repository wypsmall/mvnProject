package com.neo.lbs;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrLbsSearch {

	public static void main(String[] args) {
		try {
			// {!geofilt score=distance pt=66.3838173,39.9629015 sfield=geo_str d=100}
			// http://10.144.52.195:8080/solr/goods/select?q=*%3A*&fq=%7B!geofilt+score%3Ddistance+pt%3D66.3838173%2C39.9629015+sfield%3Dgeo_str+d%3D100%7D&wt=json&indent=true
			String url = "http://10.144.52.195:8080/solr/goods";
			SolrServer server = new HttpSolrServer(url);
			SolrQuery solrQuery = new SolrQuery();
			// String queryStr = "(" + buf.toString() + ")";
			solrQuery.setQuery("*:*");
			solrQuery.set("fq", "{!geofilt score=distance pt=66.3838173,39.9629015 sfield=geo_str d=100}");
			System.out.println(solrQuery.getQuery());
			QueryResponse rsp = server.query(solrQuery);
			SolrDocumentList list = rsp.getResults();
			for (SolrDocument solrDocument : list) {
				System.out.println("id: " + solrDocument.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
