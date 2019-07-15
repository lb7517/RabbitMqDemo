package com.lb.rabbitmq.simple;

import com.lb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private final static String QUEUE_NAME = "q_test_01";

    private static Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    public static void main(String args[]) throws IOException, TimeoutException {
        LOGGER.info("sender---------------");
        //获取连接及其通道
        Connection connection = ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();

        //声明创建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //消息内容
        String message = "Hello World";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        LOGGER.info("sender: ", message);

        //关闭通道
        channel.close();
        connection.close();
    }

}
