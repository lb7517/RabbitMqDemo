package com.lb.rabbitmq.work.fair;

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

    public static void main(String args[]) throws IOException, TimeoutException, InterruptedException {
        LOGGER.info("sender--------------");
        //获取连接及其通道
        Connection connection = ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //声明创建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for(int i = 0; i < 100; i++){
            //消息内容
            String message = "" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent " + message);
            Thread.sleep(i * 10);
        }

        //关闭通道
        channel.close();
        connection.close();
    }

}
