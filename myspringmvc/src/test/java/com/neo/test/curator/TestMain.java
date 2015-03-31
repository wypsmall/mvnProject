package com.neo.test.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class TestMain {
	//public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	public final static String ZK_HOST = "127.0.0.1:2181";
	public static void main(String[] args) {
		try {
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
			CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_HOST, retryPolicy);
			client.start();
			//client.create().forPath("/test_curator","00".getBytes());
			InterProcessMutex lock = new InterProcessMutex(client, "/test_curator");
			if (lock.acquire(15, TimeUnit.SECONDS)) {
				try {
					System.out.println("do something");
				} finally {
					lock.release();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
