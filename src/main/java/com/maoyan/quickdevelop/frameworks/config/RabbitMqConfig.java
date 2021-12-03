package com.maoyan.quickdevelop.frameworks.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 猫颜
 * @date 2021 年 12 月 03 日 上午11:26
 */
@Configuration
public class RabbitMqConfig {
  @Bean
  public Queue queue(){
    return new Queue("Email_Queue");
  }
}
