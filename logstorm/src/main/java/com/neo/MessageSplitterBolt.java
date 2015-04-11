package com.neo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class MessageSplitterBolt extends BaseRichBolt {

	private static final long serialVersionUID = 3584161192475901633L;
	private static final Logger logger = LoggerFactory.getLogger(MessageSplitterBolt.class);
	private OutputCollector collector;

	@Override
	public void execute(Tuple input) {
		String record = input.getStringByField("KafkaSpout");
		logger.info("MessageSplitterBolt receive message -> {}", record);
		if (record != null && !record.trim().isEmpty()) {
			String[] words = record.split(",");
			logger.info("Emitted: word=" + words);
			collector.emit(input, new Values("tsc", Integer.valueOf(words[1])));
			collector.ack(input);
		}

	}

	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
		this.collector = collector;

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("transCount", "count"));
	}
}
