package com.neo.test.zk;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.neo.test.zk.l02.DistributedLock;

public class TestDistributedLock {
	public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	private final static String dir = "DIST";
	public static void main(String[] args) {
		try {
			DistributedLock leader = new DistributedLock(ZK_HOST, dir);
			Boolean res = true;
			leader.lock();
			System.out.println("res : " + res);
			leader.lock();
			System.out.println("res-1 : " + res);
			leader.lock();
			System.out.println("res-2 : " + res);
			leader.unlock();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static ZooKeeper createClient() throws IOException {
		ZooKeeper zk = new ZooKeeper(ZK_HOST, 60000, new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("已经触发了" + event.getType() + "事件！");
			}
		});
		return zk;
	}
}
