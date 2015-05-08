package com.neo.test.redis;

import java.util.Map;

import redis.clients.jedis.Jedis;

public class KettleRedis {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("10.144.48.197",6379);
        String value = jedis.get("T001");
        System.out.println(value);

        String[] keys = new String[]{"551ae06cdb3163cf3da04a49",
	        "551ae090db3163cf3da04a4a",
	        "551ae096db3163cf3da04a4b",
	        "551ae0a2db3163cf3da04a4c",
	        "551ae0a2db3163cf3da04a4d",
	        "551ae0a2db3163cf3da04a4e",
	        "551ae0a2db3163cf3da04a4f",
	        "551ae0a3db3163cf3da04a50"
        };
        //删除key对应的值，如果开始方的是object，后面放map，就会报错，所以这里做一下清理
        //【redis】WRONGTYPE Operation against a key holding
        //jedis.del(keys);
        for (String key : keys) {
        	Map<String, String> val = jedis.hgetAll(key);
        	System.out.println(val);
		}
        jedis.close();
	}

}
