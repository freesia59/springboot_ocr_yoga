package com.yoga.springboot_ocr_yoga.service;

public interface IMailService {
    boolean sendSimpleMail(String to,String subject,String content);
}
