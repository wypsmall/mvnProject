package com.neo.test.seq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.log4j.Logger;

public class TestZkSeq {
	public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	private final static Logger logger = Logger.getLogger(TestZkSeq.class);
	private final static Map<String, ZKSeq> seqMap = new HashMap<String, ZKSeq>();
	private final static String seqName = "transid";
	
			
	public static void main(String[] args) throws IOException {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		final CuratorFramework zkclient = CuratorFrameworkFactory.newClient(ZK_HOST, retryPolicy);
		zkclient.start();
		final ZKSeq seq = new ZKSeq(zkclient, seqName);
		ExecutorService service = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 50000; i++) {
			service.submit(new Runnable() {

				@Override
				public void run() {
					// System.out.println("==");
					getSeq(seq);
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

	private static void getSeq(ZKSeq seq) {
		try {
			long start = System.currentTimeMillis();
			long seqence = seq.get();
			start = System.currentTimeMillis() - start;
			//logger.info("cast time " + start);
			System.out.println("===================>value is : " + seqence + "  ts : " + start);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
