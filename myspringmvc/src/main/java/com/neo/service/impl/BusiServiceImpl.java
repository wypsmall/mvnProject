package com.neo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.dao.IOrderDao;
import com.neo.po.TOrder;
import com.neo.service.BusiService;

@Service("busiService")
public class BusiServiceImpl implements BusiService {

	@Autowired
	private IOrderDao orderDao;
	
	@Override
	public String getBusiCode(Map<String, String> param) {
//		ScorePointFacade scorePointFacade = null;
//		scorePointFacade.queryScorePointList(null);
		return null;
	}

	@Override
	public List<TOrder> getAllData() {
		return orderDao.getList(null);
	}

}
