package com.neo.test.redis;

import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.Assert;
import redis.clients.jedis.Jedis;

public class TRedisApi {
	private static Jedis jedis;
	private static final String STATUS_OK = "OK";
	public static void main(String[] args) {
		initJedis();
//		commonUse();
//		specialUse();
//		stringUse();
//		listUse();
//		setUse();	
		sortedSetUse();
	}

	private static void initJedis() {
		jedis = new Jedis("10.144.48.197", 6379);
	}

	/**
	* @Description:　常用调用演示 
	* @author: wangyunpeng
	* @date: 2015年5月8日上午11:57:11
	*/
	private static void commonUse() {
		String skey = "kgome";
		String sval = "gome";
		// 设置key-value
		jedis.set(skey, sval);
		String sres = jedis.get(skey);
		System.out.println("key : " + skey + ", value : " + sres);
		Assert.assertEquals(sval, sres);

		// 很直观，就跟StringBuilder的append是一样的效果
		jedis.append(skey, "-append");
		sres = jedis.get(skey);
		System.out.println("key : " + skey + ", value : " + sres);
		Assert.assertEquals("gome-append", sres);

		// 直接覆盖原来的数据
		String nval = "newGome";
		jedis.set(skey, nval);
		sres = jedis.get(skey);
		System.out.println("key : " + skey + ", value : " + sres);
		Assert.assertEquals(nval, sres);
		
		// 删除key对应的记录
		jedis.del(skey);
		sres = jedis.get(skey);
		System.out.println("key : " + skey + ", value : " + sres);
		Assert.assertNull(sres);
	}
	
	/**
	* @Description:　特殊命令示例
	* @author: wangyunpeng
	* @date: 2015年5月8日下午12:09:46
	*/
	private static void specialUse() {
		String res = "";
		// 清空数据 当前库db0
		//res = jedis.flushDB();
		// 清空数据 所有库db0 db1 ...
		//res = jedis.flushAll();
		System.out.println("status : " + res);
		//Assert.assertEquals(STATUS_OK,  res);
		
		//这个函数感觉就是直接return回来了，根本就没有保存这个key
		res = jedis.echo("foo");
		System.out.println("status : " + res);
        Assert.assertEquals("foo", res);
        
        // 判断key否存在
        boolean flag = jedis.exists("foo");
        System.out.println("flag : " + flag);
        Assert.assertTrue(!flag);
        
        // 设置数据，判断key是否存在
        res = jedis.set("key", "values");
        System.out.println("status : " + res);
        Assert.assertEquals(STATUS_OK, res);
        
        flag = jedis.exists("key");
        System.out.println("flag : " + flag);
        Assert.assertTrue(flag);
	}
	
	/**
	* @Description:　常见字符串操作
	* @author: wangyunpeng
	* @date: 2015年5月8日下午1:55:23
	*/
	private static void stringUse() {
		String skey = "kgome";
		String sval = "gome";
		// 设置key-value
		jedis.set(skey, sval);
		// 若key存在，则不存储
		Long res = jedis.setnx(skey, "newValue");
		System.out.println("res : " + res);
		Assert.assertEquals(0L, res.longValue());
		String sres = jedis.get(skey);
		System.out.println("key : " + skey + ", value : " + sres);
		Assert.assertEquals(sval, sres);
		
		// 若key不存在，则存储
		jedis.del("new" + skey);
		res = jedis.setnx("new" + skey, "newValue");
		System.out.println("res : " + res);
		Assert.assertEquals(1L, res.longValue());
		sres = jedis.get("new" + skey);
		System.out.println("key : " + "new" + skey + ", value : " + sres);
		Assert.assertEquals("newValue", sres);
		
		// 设置key的有效期，并存储数据，有效期2sec
		sres = jedis.setex(skey, 2, sval);
		System.out.println("status : " + res);
		Assert.assertEquals(STATUS_OK, sres);
		sres = jedis.get(skey);
		System.out.println("key : " + skey + ", value : " + sres);
        Assert.assertEquals(sval, sres);
        // 延时3sec
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        // 再次查询，value过期
        sres = jedis.get(skey);
        System.out.println("key : " + skey + ", value : " + sres);
        Assert.assertEquals(null, sres);
        
        // 获取并更改数据
        jedis.set(skey, sval);
        String nval = "newValue";
        sres = jedis.getSet(skey, nval);
        System.out.println("org value : " + sres);
        sres = jedis.get(skey);
        System.out.println("new value : " + sres);
        
        // 截取字符串
        sres = jedis.getrange(skey, 1, 3);
        System.out.println("range 1-3 : " + sres);
        
        // 批量操作
        jedis.mset("mset1", "mvalue1", "mset2", "mvalue2", "mset3", "mvalue3", "mset4", "mvalue4");
        Assert.assertEquals("[mvalue1, mvalue2, mvalue3, mvalue4]",jedis.mget("mset1", "mset2", "mset3", "mset4").toString());
        res = jedis.del(new String[]{"unexist","mset1", "mset2"});
        System.out.println("del num : " + res);
        Assert.assertEquals(2L, res.longValue());
	}

	/**
	* @Description:　list操作
	* @author: wangyunpeng
	* @date: 2015年5月8日下午3:35:34
	*/
	private static void listUse() {
		String skey = "lmsg";
		String[] svals = new String[]{"hello", " my", " world", "!"};
		jedis.del(skey);
		for (String val : svals) {
			jedis.rpush(skey, val);
			// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
			List<String> vals = jedis.lrange(skey, 0, -1);
			System.out.println(vals); 
		}
		// 返回list长度（size）
		Long res = jedis.llen(skey);
		System.out.println("list size is : " + res);
		
		// 添加数据
		jedis.del("lists");
        jedis.lpush("lists", "3");
        jedis.lpush("lists", "4");
        jedis.lpush("lists", "5");
        
        Assert.assertEquals("[5, 4, 3]", jedis.lrange("lists", 0, -1).toString());
        
        // rpush 与  lpush 分别是向双向链表的右、左添加数据
        
        // 排序
        // list中的values务必是可以转成double类型的才行，要是字符串会报错，可能跟我的redis版本有关，测试的redis version 2.6.12且默认升序排序
//		Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException: ERR One or more scores can't be converted into double
//      List<String> vals = jedis.sort(skey);
//		System.out.println(vals); 
        
        Assert.assertEquals("[3, 4, 5]", jedis.sort("lists").toString());
        
        // 替换list中索引为3的值
        jedis.lset(skey, 3, "...");
        List<String> vals = jedis.lrange(skey, 0, -1);
		System.out.println(vals);
		
		// 删除value是"my"的元素，3的意思是最多只删除3个，返回值是实际删除的个数
		res = jedis.lrem(skey, 3, " my");
		System.out.println(jedis.lrange(skey, 0, -1));
		System.out.println("del count : " + res);
		Assert.assertEquals(1L, res.longValue());
		
		// 删除区间以外的数据 
		// 执行命令LTRIM list 0 2，表示只保留列表list的前三个元素，其余元素全部删除
		System.out.println(jedis.lrange(skey, 0, -1));
		String sres = jedis.ltrim(skey, 0, 1);
		System.out.println("res : " + sres);
		System.out.println(jedis.lrange(skey, 0, -1));
		
		// 列表出栈
		for (int i = 0; i <= jedis.llen(skey); i++) {
			System.out.println(jedis.lrange(skey, 0, -1));
			sres = jedis.lpop(skey);
			System.out.println("res : " + sres);
		}
	}

	/**
	* @Description:　set操作
	* @author: wangyunpeng
	* @date: 2015年5月8日下午3:35:47
	*/
	private static void setUse() {
		String skey = "kset";
		jedis.del(skey);
		for (int i = 0; i < 5; i++) {
			jedis.sadd(skey, "val-" + i);
		}
		Set<String> setValues = jedis.smembers(skey);  
        System.out.println(setValues); 
        
        // 移除"val-2"
        jedis.srem(skey, "val-2");
        setValues = jedis.smembers(skey);  
        System.out.println(setValues); 
        
        boolean flag = jedis.sismember(skey, "val-2");
        System.out.println("is exist : " + flag);
        
        // 返回集合的元素个数
        Long res = jedis.scard(skey);
        System.out.println("set size is : " + res);
        
        for (int i = 0; i < res; i++) {
        	System.out.println("========================");
        	setValues = jedis.smembers(skey);  
            System.out.println(setValues);
        	// 出栈  处在顺序是不固定 移除并返回集合中的一个随机元素
            System.out.println(jedis.spop(skey)); 
		}
        
        jedis.del("sets1");
        jedis.del("sets2");
        jedis.sadd("sets1", "HashSet1");  
        jedis.sadd("sets1", "HashSet1"); 
        //去重
        System.out.println("size : " + jedis.scard("sets1"));
        jedis.sadd("sets1", "SortedSet1");  
        jedis.sadd("sets1", "TreeSet");
        jedis.sadd("sets2", "HashSet2");  
        jedis.sadd("sets2", "SortedSet1");  
        jedis.sadd("sets2", "TreeSet1");  
        // 交集  
        System.out.println(jedis.sinter("sets1", "sets2"));  
        // 并集  
        System.out.println(jedis.sunion("sets1", "sets2"));  
        // 差集  
        System.out.println(jedis.sdiff("sets1", "sets2"));  
	}

	/**
	* @Description:　sorted set 操作 
	* @author: wangyunpeng
	* @date: 2015年5月8日下午3:52:56
	*/
	private static void sortedSetUse() {
		String skey = "ksortedSet";
		jedis.del(skey);
		for (int i = 0; i < 5; i++) {
			Random rand = new Random(System.currentTimeMillis());
			jedis.zadd(skey, rand.nextInt(450), "val-" + i);
		}
		
		// 元素个数  
        System.out.println(jedis.zcard(skey));
        
        // 元素下标  
        System.out.println(jedis.zscore(skey, "val-2")); 
        
        // 集合子集  
        System.out.println(jedis.zrange(skey, 0, -1)); 
        
        Set<String> sets = jedis.zrange(skey, 0, -1);
        for (String set : sets) {
        	System.out.println(jedis.zscore(skey, set));
		}
        
        // score值在min和max之间的成员的数量。
        System.out.println(jedis.zcount(skey, 100, 335)); 
	}
	
	private static void hashUse() {
		
	}
}
