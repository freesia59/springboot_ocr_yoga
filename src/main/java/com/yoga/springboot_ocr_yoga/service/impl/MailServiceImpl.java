package com.yoga.springboot_ocr_yoga.service.impl;

import com.yoga.springboot_ocr_yoga.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    //用于发送文件
    private JavaMailSender mailSender;

    @Override
    public boolean sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);//收信人
        mailMessage.setSubject(subject);//主题
        mailMessage.setText(content);//内容
        mailMessage.setFrom("1097788108@qq.com");
        mailSender.send(mailMessage);
//        Properties properties = new Properties();
//        properties.setProperty("mail.transport.protocol", "smtp");// 设置传输协议
//      properties.put("mail.smtp.host", "smtp.qq.com");// 设置发信邮箱的smtp地址
//      properties.setProperty("mail.smtp.auth", "true"); // 验证
//      String from = "1097788108@qq.com";// 发信邮箱
        // Authenticator auth = new A(from, "11111111"); // 使用验证，创建一个Authenticator
        return true;
    }
}
