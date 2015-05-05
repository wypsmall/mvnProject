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
	public final static String ZK_HOST = "10.144.48.195:2181";// 172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181
	public static void main(String[] args) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		final CuratorFramework zkclient = CuratorFrameworkFactory.newClient(ZK_HOST, retryPolicy);
		zkclient.start();
		//testMutex(zkclient);
		testSemaphoreMutex(zkclient);
		CloseableUtils.closeQuietly(zkclient);
	}
	
	/**
	* 可重入锁
	*/
	private static void testMutex(CuratorFramework client) {
		InterProcessMutex lock = null;
		try {
			lock = new InterProcessMutex(client, "/test_curator");
			boolean res = false;
			res = lock.acquire(10, TimeUnit.SECONDS);
			System.out.println("InterProcessMutex not get lock-1" + res);
			
			//不阻塞
			res = lock.acquire(10, TimeUnit.SECONDS); 
			System.out.println("InterProcessMutex not get lock-2" + res);
			
			System.out.println("InterProcessMutex do something");
			lock.release();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//但是要做两次release操作
				lock.release();
				//client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("release lock");
		}
	}
	
	/**
	* 不可重入锁
	*/
	private static void testSemaphoreMutex(CuratorFramework client) {
		InterProcessSemaphoreMutex lock = null;
		try {
			lock = new InterProcessSemaphoreMutex(client, "/test_curator");
			boolean res = false;
			res = lock.acquire(10, TimeUnit.SECONDS);
			System.out.println("InterProcessSemaphoreMutex not get lock-1" + res);
			
			//阻塞，并且返回false
			res = lock.acquire(10, TimeUnit.SECONDS); 
			System.out.println("InterProcessSemaphoreMutex not get lock-2" + res);
			
			System.out.println("InterProcessSemaphoreMutex do something");
			//只需一次释放锁
			lock.release();
			System.in.read();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//第二次释放会抛异常
				lock.release();
				//client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("release lock");
		}
	}
}
