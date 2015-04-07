package com.neo.test.seq;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.RetryNTimes;
import org.apache.log4j.Logger;

public class ZKSeq {
	private static final Logger log = Logger.getLogger(ZKSeq.class);
	private long blockSize;
	private long startValue;
	private long currentValue;
	private long endValue;
	private CuratorFramework client;
	private String BASE_PATH = "/zk_seq/";
	private DistributedAtomicLong count;
	private String seqName;
	
	
	public ZKSeq(CuratorFramework client, String seqName) {
		this.blockSize = 20;
		this.startValue = 0L;
		this.currentValue = 0L;
		this.endValue = 0L;
		this.client = client;
		this.seqName = seqName;
		this.count = new DistributedAtomicLong(this.client, BASE_PATH+this.seqName , new RetryNTimes(10, 10));
	}

	private void getNextBlock() {
		try {
			AtomicValue<Long> value = null;
			boolean flag = false;
			do {
				value = count.add(blockSize);
				flag = value.succeeded();
			} while (!flag);
			this.startValue = value.preValue();
			this.currentValue = value.preValue();
			this.endValue = value.postValue();
			log.info(startValue + "-" + currentValue + "-" + endValue);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	
	public synchronized long get() {
		if(this.startValue == 0)
			getNextBlock();
		if(this.currentValue == this.endValue)
			getNextBlock();
		
		return ++this.currentValue;
	}
	

}
