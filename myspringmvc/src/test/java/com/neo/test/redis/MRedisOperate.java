package com.neo.test.redis;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.neo.po.TOrder;

public class MRedisOperate {
	public static void main(String[] args) throws ScriptException {
		//使用redis计数和数据过期来实现访问限制次数
        Jedis jedis = new Jedis("10.144.48.197",6379);
        String value = jedis.get("foo");
        System.out.println(value);
        if(null == value) {
        	System.out.println("value not exist");
        	jedis.setex("foo", 10, "0");
        } else {
        	System.out.println("value is existed");
        	jedis.incrBy("foo", 1);
        }
        value = jedis.get("foo");
        System.out.println(value);
        
        TOrder order = new TOrder();
        order.setOrderId("fdas");
        jedis.hset("online_users", "user_id", JSON.toJSONString(order));
        String res = jedis.hget("online_users", "user_id");
        System.out.println(res);
        
//        ScriptEngineManager manager = new ScriptEngineManager();
//		ScriptEngine engine = manager.getEngineByName("js");
//		Object obj = engine.eval("var data = {id:100, name:'fdas'}; JSON.stringify( data )");
//		System.out.println(obj);
		
		jedis.set("test:order", "123");
		jedis.close();
	}
}
