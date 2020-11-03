package com.yoga.springboot_ocr_yoga.mq;

import com.yoga.springboot_ocr_yoga.model.MQMessage;
import com.yoga.springboot_ocr_yoga.service.IMailService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RocketMQMessageListener(consumerGroup = "consumer-group-2", topic = "rocket-topic-2")
public class MailListener implements RocketMQListener<MQMessage> {

    @Resource
    IMailService mailService;

    @Override
    public void onMessage(MQMessage mqMessage) {
        if (mqMessage.getType() == 1) {
            mailService.sendSimpleMail("yueyong1030@outlook.com", "图片识别结果", mqMessage.getData());
        }

    }
}
