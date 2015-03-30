package com.neo.test.zk.l02;

import com.neo.test.zk.l02.ConcurrentTest.ConcurrentTask;

public class ZkTestLock {
	public final static String ZK_HOST = "172.19.253.121:2181,172.19.253.122:2181,172.19.253.123:2181";
	public static void main(String[] args) {
        Runnable task1 = new Runnable(){
            public void run() {
                DistributedReentrantLock lock = null;
                try {
                    lock = new DistributedReentrantLock(ZK_HOST,"test1");
                    lock.lock();
                    Thread.sleep(3000);
                    System.out.println("===Thread " + Thread.currentThread().getId() + " running");
                } catch (Exception e) {
                    e.printStackTrace();
                } 
                finally {
                    if(lock != null)
                        lock.unlock();
                }
                 
            }
             
        };
        new Thread(task1).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        /*ConcurrentTask[] tasks = new ConcurrentTask[60];
        for(int i=0;i<tasks.length;i++){
            ConcurrentTask task3 = new ConcurrentTask(){
                public void run() {
                    DistributedReentrantLock lock = null;
                    try {
                        lock = new DistributedReentrantLock(ZK_HOST,"test2");
                        lock.lock();
                        System.out.println("Thread " + Thread.currentThread().getId() + " running");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } 
                    finally {
                        lock.unlock();
                    }
                     
                }
            };
            tasks[i] = task3;
        }
        new ConcurrentTest(tasks);*/
    }
}
