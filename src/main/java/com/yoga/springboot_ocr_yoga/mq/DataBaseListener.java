package com.yoga.springboot_ocr_yoga.mq;

import com.yoga.springboot_ocr_yoga.model.ImgMsg;
import com.yoga.springboot_ocr_yoga.model.MQMessage;
import com.yoga.springboot_ocr_yoga.service.IOcrService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@RocketMQMessageListener(consumerGroup = "consumer-group-1", topic = "rocket-topic-2")
public class DataBaseListener implements RocketMQListener<MQMessage> {

    @Resource
    IOcrService ocrService;

    @Override
    public void onMessage(MQMessage msg) {
        ImgMsg imgMsg = new ImgMsg();
        Date date = new Date();
        imgMsg.setTime(date);
        imgMsg.setImgResult(msg.getData());
        String base64 = ocrService.img2Base64();
        imgMsg.setImg2base64(base64);
        if (msg.getType() == 2) {
            boolean flag = ocrService.saveImgMsg(imgMsg);
            if (!flag) {
                System.out.println("存入数据库失败");
            } else {
                System.out.println("存入数据库成功");
            }
        }
    }
}
