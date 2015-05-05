package com.neo.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neo.dao.IOrderDao;
import com.neo.po.TOrder;

@Repository("orderDao")
public class OrderDaoImpl extends SqlSessionDaoSupport implements IOrderDao {
	protected static final Logger log = LoggerFactory.getLogger(OrderDaoImpl.class);

	@Autowired
	private SqlSessionTemplate sessionTemplate;

	public SqlSessionTemplate getSessionTemplate() {
		return sessionTemplate;
	}

	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public SqlSession getSqlSession() {
		return super.getSqlSession();
	}
	
	@Override
	public long insert(TOrder order) {
		int result = sessionTemplate.insert(getStatement("insert"), order);

		if (result <= 0) {
			throw new RuntimeException("数据库操作,insert返回 " + getStatement("insert"));
		}
		return result;
	}

	
	public String getStatement(String sqlId) {
		String name = this.getClass().getName();
		StringBuffer sb = new StringBuffer();
		sb.append(name).append(".").append(sqlId);
		String statement = sb.toString();

		return statement;
	}

	@Override
	public List<TOrder> getList(Map<String, Object> paramMap) {
		return sessionTemplate.selectList(getStatement("listAll"), paramMap);
	}
}
