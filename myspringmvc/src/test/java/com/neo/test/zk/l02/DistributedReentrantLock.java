package com.neo.test.zk.l02;

import java.util.concurrent.locks.ReentrantLock;

public class DistributedReentrantLock extends DistributedLock {
	private ReentrantLock reentrantLock = new ReentrantLock();

	public DistributedReentrantLock(String config, String lockName) {
		super(config, lockName);
	}

	@Override
	public void lock() {
		reentrantLock.lock();// 多线程竞争时，先拿到第一层锁
		super.lock();
	}

	@Override
	public boolean tryLock() {
		// 多线程竞争时，先拿到第一层锁
		return reentrantLock.tryLock() && super.tryLock();
	}

	@Override
	public void unlock() {
		super.unlock();
		reentrantLock.unlock();// 多线程竞争时，释放最外层锁
	}

}
