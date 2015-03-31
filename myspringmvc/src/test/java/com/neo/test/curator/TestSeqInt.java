package com.neo.test.curator;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class TestSeqInt {
	public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";

	public static void main(String[] args) throws IOException {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		final CuratorFramework zkclient = CuratorFrameworkFactory.newClient(ZK_HOST, retryPolicy);
		zkclient.start();
		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			service.submit(new Runnable() {

				@Override
				public void run() {
					// System.out.println("==");
					getSeq(zkclient);
					try {
						// Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		service.shutdown();
		System.in.read();
		CloseableUtils.closeQuietly(zkclient);
	}

	private static void getSeq(CuratorFramework client) {
		try {
/*			SharedCount baseCount = new SharedCount(client, "/seq/counter", 0);
			baseCount.start();
			System.out.println("baseCount.getVersionedValue()==>" + baseCount.getVersionedValue());
			boolean flag = baseCount.trySetCount(baseCount.getVersionedValue(), baseCount.getCount() + 1);
			if (flag)
				System.out.println("get new seq succeed==>" + baseCount.getCount());
			else
				System.out.println("get new seq error====>" + baseCount.getCount());
			while(baseCount.trySetCount(baseCount.getVersionedValue(), baseCount.getCount() + 1)) {
				System.out.println("get new seq succeed==>" + baseCount.getCount());
				break;
			}
			baseCount.close();*/
			
			boolean flag = false;
			do {
				SharedCount baseCount = new SharedCount(client, "/seq/counter", 0);
				baseCount.start();
				flag = baseCount.trySetCount(baseCount.getVersionedValue(), baseCount.getCount() + 1);
				baseCount.close();
			} while (!flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
