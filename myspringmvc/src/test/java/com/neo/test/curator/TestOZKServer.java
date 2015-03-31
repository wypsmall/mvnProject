package com.neo.test.curator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class TestOZKServer {
	public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	public static void main(String[] args) {
		final CuratorFramework zkclient = CuratorFrameworkFactory.newClient(ZK_HOST, new ExponentialBackoffRetry(1000, 3));
		zkclient.start();
		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1; i++) {
			
			service.submit(new Runnable() {
				
				@Override
				public void run() {
					//System.out.println("==");
					operate(zkclient);
					try {
						//Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		service.shutdown();
	}
	
	private static void operate(CuratorFramework client) {
		InterProcessMutex lock = null;
		//CuratorFramework client = null;
		try {
			//client = CuratorFrameworkFactory.newClient(ZK_HOST, new ExponentialBackoffRetry(1000, 3));
			//client.start();
			lock = new InterProcessMutex(client, "/test_curator");
			while (!lock.acquire(100, TimeUnit.SECONDS)) {
				System.out.println("not get lock");
			}
			System.out.println("do something");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				lock.release();
				//client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("release lock");
		}
	}
}
