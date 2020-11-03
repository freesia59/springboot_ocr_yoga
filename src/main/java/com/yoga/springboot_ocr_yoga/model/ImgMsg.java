package com.yoga.springboot_ocr_yoga.model;

import java.util.Date;

public class ImgMsg {
    private Long id;
    private String img2base64;
    private String imgResult;
    private Date time;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg2base64() {
        return img2base64;
    }

    public void setImg2base64(String img2base64) {
        this.img2base64 = img2base64;
    }

    public String getImgResult() {
        return imgResult;
    }

    public void setImgResult(String imgResult) {
        this.imgResult = imgResult;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
