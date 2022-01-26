package com.maoyan.quickdevelop.common.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author 猫颜
 * @date 2021 年 12 月 03 日 上午10:51
 * 生产者
 */
@Component
public class ProucerUtil {
  @Autowired
  private RabbitTemplate rabbitTemplate;
  private final static String QUEUE_NAME = "Email_Queue";
  public void send(HashMap<String,String> message){
    this.rabbitTemplate.convertAndSend(QUEUE_NAME,message);
  }
//  public static void publishMessage(String message) throws IOException, TimeoutException {
//    ConnectionFactory connectionFactory = new ConnectionFactory();
//    connectionFactory.setHost("localhost");
//    connectionFactory.setUsername("guest");
//    connectionFactory.setPassword("guest");
//    Connection connection = connectionFactory.newConnection();
//    Channel channel = connection.createChannel();
//    channel.queueDeclare(QUEUE_NAME,false,true,false,null);
//    channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
//  }
}
