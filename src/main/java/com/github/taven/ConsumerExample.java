package com.github.taven;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerExample {
    public static void main(String[] args) {
        String groupId = "ConsumerExample";

        Properties props = new Properties();
        //kafka消费的的地址
        props.put("bootstrap.servers", "192.168.3.21:9092");
        //组名 不同组名可以重复消费
        props.put("group.id", groupId);
        //是否自动提交
        props.put("enable.auto.commit", "true");
        //从poll(拉)的回话处理时长
        props.put("auto.commit.interval.ms", "1000");
        //超时时间
        props.put("session.timeout.ms", "30000");
        //一次最多拉取的条数
        props.put("max.poll.records", 1000);
//		earliest
//		当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
//		latest
//		当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
//		none
//		topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        props.put("auto.offset.reset", "earliest");
        //序列化
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "topic_a";
        //订阅主题列表topic
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

            if (!records.isEmpty()) {
                records.forEach(record -> {
                    System.out.println(record);
                });
            }
        }

        // 查看消费者对应的topic和分区
        // bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group connect-es-sink-dev
    }
}
