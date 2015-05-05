package com.neo.dao;

import java.util.List;
import java.util.Map;

import com.neo.po.TOrder;

public interface IOrderDao {
	long insert(TOrder order);
	List<TOrder> getList(Map<String, Object> paramMap);
}
