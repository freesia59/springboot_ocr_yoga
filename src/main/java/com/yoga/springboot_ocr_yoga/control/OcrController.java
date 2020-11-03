package com.yoga.springboot_ocr_yoga.control;

import com.yoga.springboot_ocr_yoga.model.ApiResult;
import com.yoga.springboot_ocr_yoga.service.IOcrService;
import com.yoga.springboot_ocr_yoga.service.impl.MailServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OcrController {

    @Autowired
    private IOcrService ocrService;
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    // 发送给Broker，默认会自动创建topic，topic和tag用冒号分隔

    @PostMapping("/ocr")
    @ResponseBody
    public ApiResult orc(@RequestParam("fileName") MultipartFile multipartFile) {
        return ocrService.getMsgFromImg(multipartFile);
    }

}
