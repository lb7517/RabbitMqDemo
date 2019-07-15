package com.lb.rabbitmq.util;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        //定义工厂
        ConnectionFactory cf = new ConnectionFactory();
        //设置服务
        cf.setHost("127.0.0.1");
        //端口
        cf.setPort(5672);
        //设置账号信息，用户名，密码，vhost
        cf.setPassword("lb");
        cf.setUsername("lb");
        cf.setVirtualHost("/mall"); //注意别少"/"
        //通过工程获取连接
        Connection connection = cf.newConnection();
        return connection;
    }

}
