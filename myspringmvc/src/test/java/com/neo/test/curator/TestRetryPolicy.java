package com.neo.test.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;

public class TestRetryPolicy {
	//public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	public final static String ZK_HOST = "127.0.0.1:2181";
	public static void main(String[] args) {
		CuratorFramework client = null;
		RetryPolicy retryPolicy = null;
		try {
			retryPolicy = new RetryOneTime(3000);
			//retryPolicy = new RetryNTimes(5, 3000);
			//retryPolicy = new ExponentialBackoffRetry(1000, 3);
			
			
			client = CuratorFrameworkFactory.newClient(ZK_HOST, retryPolicy);
			client.start();
			client.getCuratorListenable().addListener(new CuratorListener() {
				
				@Override
				public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
					System.out.println("=====================event:" + event.getName());
				}
			});
			client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
				
				@Override
				public void stateChanged(CuratorFramework client, ConnectionState newState) {
					System.out.println("=====================newState:" + newState.name());
				}
			});
			client.create().withMode(CreateMode.EPHEMERAL).forPath("/tt_c");
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//client.close();
			CloseableUtils.closeQuietly(client);
		}
	}
}
