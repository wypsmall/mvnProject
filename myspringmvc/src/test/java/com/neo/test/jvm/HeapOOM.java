package com.neo.test.jvm;

import java.util.ArrayList;
import java.util.List;

/** 
 * desc:
 * <p>创建人：wangyunpeng 创建日期：2015年3月15日</p>
 * @version V1.0  
 * 
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * -XX:+PrintGCDetails -verbose:gc
 */
public class HeapOOM {
	
	static class OOMObject{
		
	}

	public static void main(String[] args) throws Exception {
		List<OOMObject> list = new ArrayList<OOMObject>();
		int count = 0;
		while(true) {
			list.add(new OOMObject());
			count++;
			if(count % 500 == 0) {
				System.gc();
				count = 0;
				Thread.sleep(50);
			}
		}
	}
}
