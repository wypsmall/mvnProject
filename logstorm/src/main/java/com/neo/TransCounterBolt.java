package com.neo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class TransCounterBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1210458926027062186L;
	private static final Logger logger = LoggerFactory.getLogger(TransCounterBolt.class);
	private OutputCollector collector;
	Connection conn = null;
	private int curId;
	private String curDate;
	private final Map<String, AtomicInteger> counterMap = new HashMap<String, AtomicInteger>();

	@Override
	public void execute(Tuple input) {
		String word = input.getString(0);
		int count = input.getIntegerByField("count"); // 通过Field名称取出对应字段的数据
		AtomicInteger ai = counterMap.get(word);
		if (ai == null) {
			ai = new AtomicInteger(0);
			counterMap.put(word, ai);
		}
		ai.addAndGet(count);
		UpdateDB(ai.get());
		logger.info("DEBUG: word=" + word + ", count=" + ai.get());
		collector.ack(input);

	}

	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
		this.collector = collector;
		try {
			LinkDB();
			InsertDB();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer collector) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup() {
		//super.cleanup();
		// print count results
		logger.info("Word count results:");
		for (Entry<String, AtomicInteger> entry : counterMap.entrySet()) {
			logger.info("\tword=" + entry.getKey() + ", count=" + entry.getValue().get());
		}

	}

	private void LinkDB() throws InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		String host_port = "192.168.1.103:3306";
		String database = "test";
		String username = "root";
		String password = "123456";
		String url = "jdbc:mysql://" + host_port + "/" + database;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			logger.info("=================================link mysql==========================");
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private void InsertDB() {
		curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String sql = "insert into stormstatistic (transdate, amount) values ('" + curDate + "'," + 0 + ")";
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			curId = rs.getInt(1);
			logger.info("===========>sql[{}], res:[{}]" , new Object[]{sql, curId});
		} catch (SQLException e) {
			logger.error("", e);
		}
	}

	private void UpdateDB(int num) {
		String sql = "update stormstatistic set amount=" + num + " where id=" + curId + " and transdate='" + curDate + "'";
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			logger.info("===========>sql[{}], res:[{}]" , new Object[]{sql, curId});
		} catch (SQLException e) {
			logger.error("", e);
		}
	}

	public static void main(String[] args) {
		try {

			TransCounterBolt b = new TransCounterBolt();
			b.LinkDB();
			b.InsertDB();
			b.UpdateDB(5);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
