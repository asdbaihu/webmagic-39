package com.myresource.rabbitmq.demo5;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public final static String EXCHANGE_NAME="demo4";

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

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey  = getRouting(args);
            String message = getMessage(args);

            //发送消息到队列中
//            String message = "Hello RabbitMQ";
            channel.basicPublish(EXCHANGE_NAME,routingKey , null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey  + "':'" + message + "'");

            //关闭通道和连接
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getRouting(String[] strings){
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] strings){
        if (strings.length < 2) {
            return "Hello World!";
        }
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0 ){ return "";}
        if (length < startIndex ){ return "";}
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
