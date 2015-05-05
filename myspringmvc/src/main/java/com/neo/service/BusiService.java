package com.neo.service;

import java.util.List;
import java.util.Map;

import com.neo.po.TOrder;

public interface BusiService {
	/**
	* @Description:　TODO 
	* @author: wangyunpeng
	* @date: 2015年3月25日上午9:03:33
	* @param param
	* @return
	*/
	public String getBusiCode(Map<String, String> param);
	
	public List<TOrder> getAllData();
}
