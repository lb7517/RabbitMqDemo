package com.lb.rabbitmq.work.fair;

import com.lb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver2 {

    private final static String QUEUE_NAME = "q_test_01";

    private final static Logger LOGGER = LoggerFactory.getLogger(Receiver2.class);

    private static int total = 0;

    public static void main(String args[]) throws IOException, TimeoutException {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received " + message + ", total: "+ total++);
            //休眠
            try {Thread.sleep(10000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                // 返回确认状态，注释掉表示使用自动确认模式
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        //autoAck及第二个参数，true表示自动确认
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }
}
