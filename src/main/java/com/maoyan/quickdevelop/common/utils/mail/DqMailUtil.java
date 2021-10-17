package com.maoyan.quickdevelop.common.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class DqMailUtil {
  @Autowired
  private JavaMailSenderImpl javaMailSender;
  @Value("${spring.mail.username}")
  private String username;

  /**
   * 发送邮件
   */
  public void sendMail(String subject, String toMail, String message) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    // 邮件配置
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText(message);
    simpleMailMessage.setTo(toMail);
    simpleMailMessage.setFrom(username);
    System.out.println(simpleMailMessage);
    javaMailSender.send(simpleMailMessage);
  }

  /**
   * 发送模板邮件
   * @param subject
   * @param toMail
   * @param message
   * @throws MessagingException
   */
  public void sendMailByThymeleaf(String subject, String toMail, String message) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setFrom(username);
    mimeMessageHelper.setTo(toMail);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(message,true);
    javaMailSender.send(mimeMessage);
  }
}
