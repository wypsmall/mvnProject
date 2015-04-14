package com.neo.mongodb;

import java.util.Arrays;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * Hello world!
 * 
 */
public class MongodbLBS {
	public static void main(String[] args) {
		try {
			Mongo mongo = new Mongo("10.144.52.195", 27017);
			DB db = mongo.getDB("point");

			DBCollection col = db.getCollection("point.test");

			Set<String> colls = db.getCollectionNames();
			for (String s : colls) {
				System.out.println(s);
			}

			DBCursor cur = col.find();
			if (null == cur || cur.size() <= 0) {
				System.out.println("no data");
			}

			while (cur.hasNext()) {
				DBObject dbObj = cur.next();
				System.out.println(dbObj);
			}
			
			/*
			 * db.point.test.find({loc:{$near: {$geometry: {type: "Point" ,coordinates: [118.783799,31.979234]},$maxDistance: 5000}}})
			 * { "loc" : { "$near" : { "$geometry" : { "type" : "Point" , "coordinates" : [ 118.783799 , 31.979234]}} , "$maxDistance" : 5000}}
			 */

			BasicDBObject param = new BasicDBObject("loc", new BasicDBObject("$near", new BasicDBObject("$geometry", new BasicDBObject("type", "Point").append("coordinates", Arrays.asList(118.783799,31.979234))).append("$maxDistance",3000)));
			System.out.println(param.toString());
			cur = col.find(param);
			while (cur.hasNext()) {
				DBObject dbObj = cur.next();
				System.out.println(dbObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
