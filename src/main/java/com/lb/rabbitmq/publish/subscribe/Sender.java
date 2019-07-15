package com.lb.rabbitmq.publish.subscribe;

import com.lb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private final static String QUEUE_NAME = "test_queue_work1";

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    private static Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    public static void main(String args[]) throws IOException, TimeoutException {
        LOGGER.info("订阅模式 sender---------------");
        //获取连接及其通道
        Connection connection = ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();

        //声明创建队列
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //消息内容
        String message = "publish/subscribe Hello World";
        channel.basicPublish(EXCHANGE_NAME,"", null, message.getBytes());

        LOGGER.info("订阅模式 sender: ", message);

        //关闭通道
        channel.close();
        connection.close();
    }

}
