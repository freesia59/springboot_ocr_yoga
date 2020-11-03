package com.yoga.springboot_ocr_yoga.model;

import java.io.Serializable;

public class MQMessage implements Serializable {
    private static final long serialVersionUID = 3414951084514933083L;
    // 消息的种类，1为发邮件，2为存数据库
    private int type;
    // 消息的实际内容
    private String data;

    public MQMessage() {
    }

    public MQMessage(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
