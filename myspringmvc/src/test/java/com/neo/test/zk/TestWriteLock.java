package com.neo.test.zk;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.neo.test.zk.l01.WriteLock;

public class TestWriteLock {
	public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	private final static String dir = "/WRITE_LOCK";
	private static int count = 0;
	public static void main(String[] args) {
		try {
			ZooKeeper keeper = createClient();
			WriteLock leader = new WriteLock(keeper, dir, null);
			Boolean res = leader.lock();
			System.out.println("res : " + res);
			for (int i = 0; i < 1000; i++) {
				while(!leader.lock()) {
					System.out.println("res : " + res);
					count++;
				}
				System.out.println("do something");
				leader.unlock();
			}
//			res = leader.lock();
//			System.out.println("res-1 : " + res);
//			res = leader.lock();
//			System.out.println("res-2 : " + res);
//			leader.unlock();
			System.out.println("count ==> " + count);
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
