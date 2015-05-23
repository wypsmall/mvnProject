/**
 * <p>Copyright: Copyright(c) 2015</p>
 * <p>Company: gome.com.cn</p>
 * <p>2015年2月27日下午6:11:22</p>
 * @author wangyunpeng
 * @version 1.0
 */
package neo.ce.service.impl;

import neo.ce.service.IVirtualService;

/** 
 * desc:
 * <p>创建人：wangyunpeng 创建日期：2015年2月27日下午6:11:22</p>
 * @version V1.0  
 */
public class VirtualServiceImpl implements IVirtualService {

	@Override
	public String hello(String name) {
		//用来测试方法重入了几次
		System.out.println("enter method..." + System.currentTimeMillis());
		System.out.println(this);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("return..." + name);
		return "hello : " + name;
	}

}
