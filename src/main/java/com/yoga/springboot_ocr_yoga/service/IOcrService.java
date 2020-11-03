package com.yoga.springboot_ocr_yoga.service;

import com.yoga.springboot_ocr_yoga.model.ApiResult;
import com.yoga.springboot_ocr_yoga.model.ImgMsg;
import org.springframework.web.multipart.MultipartFile;

public interface IOcrService {
    ApiResult getMsgFromImg(MultipartFile multipartFile);

    boolean saveImg(MultipartFile multipartFile);

    String ocr();

    String img2Base64();

    boolean saveImgMsg(ImgMsg imgMsg);

}
