package com.lb.rabbitmq.topics;

import com.lb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String args[]) throws IOException, TimeoutException {
        //获取到连接及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明exchange, direct交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String message = "topic Hello World!";
        //delete表示消息key
        channel.basicPublish(EXCHANGE_NAME, "routeKey.1", null, message.getBytes());
        System.out.println("topics [x] Sent "+message);

        channel.close();
        connection.close();
    }

}
