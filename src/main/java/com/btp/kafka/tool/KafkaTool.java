package com.btp.kafka.tool;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.btp.elasticsearch.tool.ElasticsearchTool;

/**
 * kafka交互工具
 * @author baitp
 *
 */
public class KafkaTool {
	 private static Properties props;

	 static{
		 props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("group.id", "testConsumer");
		 props.put("enable.auto.commit", "true");
		 props.put("auto.commit.interval.ms", "1000");
		 props.put("session.timeout.ms", "30000");
		 props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		 props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	 }

	 public static void consume() {
		 System.err.println("启动kafka消费进程");
		 connectionKafka();
		 System.err.println("停止kafka消费进程");
	 }

    @SuppressWarnings("resource")
    static void connectionKafka() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
            	//向elasticsearch中存放数据
                System.err.println("kafka消费端收到消息：" + record.value());
                //ElasticsearchTool.dataToElas("exceptions1",record.value());
                ElasticsearchTool.mulDataToElas("myexceptions", "exception", Arrays.asList((Object)(record.value())));
            }
        }
    }
    
    public static void main(String...args) {
    	consume();
    }
}
