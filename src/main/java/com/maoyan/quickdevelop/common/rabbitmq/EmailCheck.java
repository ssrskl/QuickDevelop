package com.maoyan.quickdevelop.common.rabbitmq;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.maoyan.quickdevelop.common.constant.HttpStatus;
import com.maoyan.quickdevelop.common.exception.CustomException;
import com.maoyan.quickdevelop.common.utils.mail.DqMailUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 猫颜
 * @date 2021 年 12 月 03 日 上午11:47
 */
@Component
@RabbitListener(queues = "Email_Queue")
public class EmailCheck {
  @Autowired
  private DqMailUtil dqMailUtil;
  @Autowired
  private TemplateEngine templateEngine;

  @RabbitHandler
  public void receiver(String emailMessage) {
    // 获取IP
    InetAddress address = null;
    try {
      address = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    System.out.println("接收到的数据:" + emailMessage);
    // 解析JSON内容
    JSONObject jsonObject = JSONUtil.parseObj(emailMessage);
    String dqUserUsername = (String) jsonObject.get("DqUserUsername");
    String dqUserEmail = (String) jsonObject.get("DqUserEmail");
    String emailVerificationCode = (String) jsonObject.get("EmailVerificationCode");
    // 邮件正文
    Context emailContext = new Context();
    emailContext.setVariable("toDqUser", dqUserUsername);
    emailContext.setVariable("emailVerificationUrl", "http://"+address+":8080/verification/" + emailVerificationCode);
    String emailContent = templateEngine.process("emailCheckTemplate", emailContext);
    try {
      dqMailUtil.sendMailByThymeleaf("邮箱验证", "1071352028@qq.com",
              emailContent);
    } catch (MessagingException e) {
      throw new CustomException("邮件发送失败", HttpStatus.ERROR);
    }
  }
}
