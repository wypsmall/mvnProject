package com.neo.mongodb;

import java.util.Arrays;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

public class MongodbInitData {
	public static void main(String[] args) {
		try {
			Mongo mongo = new Mongo("10.144.52.195", 27017);
			DB db = mongo.getDB("point");

			DBCollection col = db.getCollection("shopinfo");
			if(null != col) {
				System.out.println(col);
				//db.point.test.insert({"address" : "南京 禄口国际机场","loc" : { "type": "Point", "coordinates": [118.783799,31.979234]}})  
				
//				WriteResult wr = col.remove(new BasicDBObject());
//				System.out.println("result : " + wr.getN());
				
				Random rand = new Random(System.currentTimeMillis());
				for (int i = 0; i < 20; i++) {
					BasicDBObject shop = new BasicDBObject();
					shop.put("shopid", "SI0000"+i);
					shop.put("address", "北京三元桥店-" + i);
					int x = rand.nextInt(15)+1;
					int y = rand.nextInt(15)+1;
					System.out.println("x:" + x/100.0 + ", y:"+y/100.0);
					BasicDBObject location = new BasicDBObject().append("type", "Point").append("coordinates", Arrays.asList(x/100.0 + 118.783799, y/100.0 + 31.979234));
					shop.put("location", location);
					//col.insert(shop);
					
				}
				
				//db.shopinfo.ensureIndex( { location : "2dsphere" } ) 
//				BasicDBObject index = new BasicDBObject("location", "2dsphere");
//				col.ensureIndex(index);
				
				
				BasicDBObject param = new BasicDBObject("location", 
						new BasicDBObject("$near", 
								new BasicDBObject("$geometry", 
										new BasicDBObject("type", "Point")
										.append("coordinates", Arrays.asList(118.783799,31.979234)))
										.append("$maxDistance",10000)));
				System.out.println(param.toString());
				DBCursor cur = col.find(param);
				System.out.println("result size :" + cur.count());
				while (cur.hasNext()) {
					DBObject dbObj = cur.next();
					System.out.println(dbObj);
				}
			}
			
			mongo.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
