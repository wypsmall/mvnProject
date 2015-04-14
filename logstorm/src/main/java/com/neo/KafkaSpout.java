package com.neo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class KafkaSpout extends BaseRichSpout {
	private static final long serialVersionUID = 1602772035366469345L;
	private SpoutOutputCollector collector;
	private static final Logger logger = LoggerFactory.getLogger(KafkaSpout.class);
	private String topic;
	private ConsumerConnector consumer;

	public KafkaSpout(String topic) {
		try {
			
			this.topic = topic;
			//this.consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
		} catch (Exception e) {
			logger.info("=========>");
			e.printStackTrace();
			logger.error("", e);
			logger.info("=========>");
		}
	}

	private static ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		// 设置zookeeper的链接地址
		props.put("zookeeper.connect", "GS01:2181,GS02:2181,GS03:2181");
		// 设置group id
		props.put("group.id", "1");
		// kafka的group 消费记录是保存在zookeeper上的, 但这个信息在zookeeper上不是实时更新的, 需要有个间隔时间更新
		props.put("auto.commit.interval.ms", "1000");
		props.put("zookeeper.session.timeout.ms", "10000");
		return new ConsumerConfig(props);
	}

	@Override
	public void nextTuple() {
		if(null == consumer) {
			consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
		}
		// 设置Topic=>Thread Num映射关系, 构建具体的流
		Map<String, Integer> topickMap = new HashMap<String, Integer>();
		topickMap.put(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(topickMap);
		KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext()) {
			String value = new String(it.next().message());
			logger.info("(consumer)-->" + value);
			collector.emit(new Values(value));
		}
//		collector.emit(new Values("fdsa,21")); 
	}

	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
		this.collector = collector;

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("KafkaSpout"));

	}

}
