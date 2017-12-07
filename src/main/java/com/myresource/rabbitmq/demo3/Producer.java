package com.myresource.rabbitmq.demo3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public final static String EXCHANGE_NAME="demo2";

    public static void main(String[] args){
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();

            //设置RabbitMQ相关信息
            factory.setHost("127.0.0.1");
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setPort(5672);

            //创建一个新的连接
            Connection connection = factory.newConnection();

            //创建一个通道
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            //发送消息到队列中
            String message = "Hello RabbitMQ";
            channel.basicPublish(EXCHANGE_NAME,"", null, message.getBytes("UTF-8"));
            System.out.println("Producer Send +'" + message + "'");

            //关闭通道和连接
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
