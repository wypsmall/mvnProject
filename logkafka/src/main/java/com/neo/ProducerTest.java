package com.neo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerTest {

	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.put("zk.connect", "GS01:2181,GS02:2181,GS03:2181");
			// serializer.class为消息的序列化类
			props.put("serializer.class", "kafka.serializer.StringEncoder");
			// 配置metadata.broker.list, 为了高可用, 最好配两个broker实例
			props.put("metadata.broker.list", "GS01:9092");
			// 设置Partition类, 对队列进行合理的划分
			// props.put("partitioner.class",
			// "idoall.testkafka.Partitionertest");
			// ACK机制, 消息发送需要kafka服务端确认
			props.put("request.required.acks", "1");

			props.put("num.partitions", "1");
			ProducerConfig config = new ProducerConfig(props);
			Producer<String, String> producer = new Producer<String, String>(config);
			for (int i = 0; i < 10; i++) {
				// KeyedMessage<K, V>
				// 　　K对应Partition Key的类型
				// 　　V对应消息本身的类型
				// 　　 topic: "test", key: "key", message: "message"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss SSS");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String str = formatter.format(curDate);

				String msg = "idoall.org" + i + "=" + str;
				String key = i + "";
				producer.send(new KeyedMessage<String, String>("idoall_testTopic", key, msg));
				System.out.println("msg:" + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
