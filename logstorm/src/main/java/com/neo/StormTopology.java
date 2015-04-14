package com.neo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class StormTopology {
	private static final Logger logger = LoggerFactory.getLogger(StormTopology.class);

	public static void main(String[] args) {
		try {
			TopologyBuilder builder = new TopologyBuilder();
//			builder.setSpout("spout", new RandomSpout());  
//	        builder.setBolt("bolt", new SenqueceBolt()).shuffleGrouping("spout"); 
			// configure 3 spouts
			builder.setSpout("spout-kafka", new KafkaSpout("kafka-flume"), Integer.valueOf(args[1]));
			builder.setBolt("bolt-splitter", new MessageSplitterBolt(), Integer.valueOf(args[2])).shuffleGrouping("spout-kafka");
			builder.setBolt("bolt-count", new TransCounterBolt(), Integer.valueOf(args[3])).fieldsGrouping("bolt-splitter", new Fields("transCount", "count"));
			// submit topology
			Config conf = new Config();  
	        conf.setDebug(true); 
			if (args != null && args.length > 0) {
				conf.setNumWorkers(Integer.valueOf(args[4]));
				StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
			} else {
				LocalCluster cluster = new LocalCluster();
				cluster.submitTopology("LocalCluster-StormTopology", conf, builder.createTopology());
				Thread.sleep(10 * 60 * 1000);
				//cluster.killTopology("LocalCluster-StormTopology");
				cluster.shutdown();
			}

		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
