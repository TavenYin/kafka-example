package com.github.taven.producer;

/**
 * 默认分区策略：
 * 如果在发消息的时候指定了分区，则消息投递到指定的分区
 * 如果没有指定分区，但是消息的key不为空，则基于key的哈希值来选择一个分区
 * 如果既没有指定分区，且消息的key也是空，则用轮询的方式选择一个分区
 */
public class ProducerPartitionExample {
    public static void main(String[] args) {

    }
}
