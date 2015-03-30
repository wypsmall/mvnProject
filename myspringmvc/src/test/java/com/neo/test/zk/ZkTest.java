package com.neo.test.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

public class ZkTest {
	public final static String ZK_HOST = "127.0.0.1:2181";//10.144.5.218

	public static void main(String[] args) {
		try {
			String path = "/gome";
			final ZooKeeper zk = new ZooKeeper(ZK_HOST, 5000, new Watcher() {
				public void process(WatchedEvent event) {
					System.out.println("已经触发了" + event.getType() + "事件！");
				}

			});
			Stat stat = zk.exists(path, false);
			if (null == stat) {
				zk.create(path, "00".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			Runtime.getRuntime().addShutdownHook(new Thread() {

				@Override
				public void run() {
					try {
						while (true) {

							Thread.sleep(3000);
							States ss = zk.getState();
							System.out.println("connect is alive : " + ss.isAlive());
							System.out.println("connect is connected : " + ss.isConnected());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
