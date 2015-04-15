package com.neo.lbs;

import java.util.Arrays;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class LBSGoodsSearch {

	public static void main(String[] args) {
		try {
			Mongo mongo = new Mongo("10.144.52.195", 27017);
			DB db = mongo.getDB("point");

			DBCollection col = db.getCollection("shopinfo");
			
			BasicDBObject param = new BasicDBObject("location", 
					new BasicDBObject("$near", 
							new BasicDBObject("$geometry", 
									new BasicDBObject("type", "Point")
									.append("coordinates", Arrays.asList(118.783799,31.979234)))
									.append("$maxDistance",10000)));
			System.out.println(param.toString());
			DBCursor cur = col.find(param);
			System.out.println("result size :" + cur.count());
			StringBuffer buf = new StringBuffer();
			while (cur.hasNext()) {
				DBObject dbObj = cur.next();
				System.out.println(dbObj);
				buf.append("shopid:");
				buf.append(dbObj.get("shopid") + " OR ");
			}
			buf.delete(buf.length()-3, buf.length());
			String queryStr = "(" + buf.toString() + ")";
			System.out.println(queryStr);
			
			String url = "http://10.144.52.195:8080/solr/goods";
			SolrServer server = new HttpSolrServer(url);
			SolrQuery solrQuery = new SolrQuery();
			//solrQuery.setQuery("(shopid:SI00001 OR shopid:SI00006) AND name:**");
			String goodsname = "机";
			solrQuery.setQuery(queryStr + " AND name:*" + goodsname + "*");
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
