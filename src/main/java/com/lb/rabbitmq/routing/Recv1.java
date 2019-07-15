package com.lb.rabbitmq.routing;

import com.lb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {

    private final static String EXCHANGE_NAME = "test_exchange_direct";

    private final static String QUEUE_NAME = "test_queue_direct_1";

    private static int total = 0;

    public static void main(String args[]) throws IOException, TimeoutException {
        //获取到连接及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //绑定队列到交换机上
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "update");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");

        //同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumeTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println("路由1 [x] Received " + message + ", total: "+ total++);
            //休眠
            try {
                Thread.sleep(10);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                // 返回确认状态，注释掉表示使用自动确认模式
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumeTag -> {});
    }

}
