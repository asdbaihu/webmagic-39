package com.myresource.rabbitmq.demo4;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer {
    private final static String EXCHANGE_NAME = "demo3";

    public static void main(String[] args){
        try {
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();

            //设置RabbitMQ地址
            factory.setHost("127.0.0.1");
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setPort(5672);

            //创建一个新的连接
            Connection connection = factory.newConnection();

            //创建一个通道
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String queueName = channel.queueDeclare().getQueue();

            if (args.length < 1){
                channel.queueBind(queueName, EXCHANGE_NAME, "info");
            }else{
                for(String severity : args){
                    channel.queueBind(queueName, EXCHANGE_NAME, severity);
                }
            }

            System.out.println("Customer Waiting Received messages");
            channel.basicQos(1);

            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Customer Received '" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, consumer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
