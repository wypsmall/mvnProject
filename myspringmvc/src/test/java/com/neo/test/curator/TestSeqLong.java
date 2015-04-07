package com.neo.test.curator;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;
import org.apache.log4j.Logger;

public class TestSeqLong {
	public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	private final static Logger logger = Logger.getLogger(TestSeqLong.class);
	public static void main(String[] args) throws IOException {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		final CuratorFramework zkclient = CuratorFrameworkFactory.newClient(ZK_HOST, retryPolicy);
		zkclient.start();
		ExecutorService service = Executors.newFixedThreadPool(50);
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

			long start = System.currentTimeMillis();
			boolean flag = false;
			DistributedAtomicLong count = new DistributedAtomicLong(client, "/seq/longs", new RetryNTimes(10, 10));
			AtomicValue<Long> value = null;
			int ts = 0;
			do {
				ts ++;
				value = count.increment();
				flag = value.succeeded();
				System.out.println("loop times is " + ts);
			} while (!flag);
			System.out.println("===================>value is : " + value.postValue());
			start = System.currentTimeMillis() - start;
			logger.info("cast time " + start);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
