package com.neo.test.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class TestZKLock {
	public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	public static void main(String[] args) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		final CuratorFramework zkclient = CuratorFrameworkFactory.newClient(ZK_HOST, retryPolicy);
		zkclient.start();
		operate(zkclient);
		CloseableUtils.closeQuietly(zkclient);
	}
	
	private static void operate(CuratorFramework client) {
		InterProcessSemaphoreMutex lock = null;
		try {
			lock = new InterProcessSemaphoreMutex(client, "/test_curator");
			while (!lock.acquire(10, TimeUnit.SECONDS)) {
				System.out.println("not get lock");
			}
			lock.acquire(10, TimeUnit.SECONDS);
			System.out.println("do something");
			lock.release();
			
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
