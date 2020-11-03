package com.yoga.springboot_ocr_yoga.service.impl;

import com.baidu.aip.ocr.AipOcr;
import com.yoga.springboot_ocr_yoga.mapper.ImgMsgMapper;
import com.yoga.springboot_ocr_yoga.model.ApiResult;
import com.yoga.springboot_ocr_yoga.model.ImgMsg;
import com.yoga.springboot_ocr_yoga.model.MQMessage;
import com.yoga.springboot_ocr_yoga.service.IOcrService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Service
public class OcrServiceImpl implements IOcrService {
    //设置APPID/AK/SK
    public static final String APP_ID = "22850350";
    public static final String API_KEY = "umGkVOnUnzTyYO7FzPTXUD09";
    public static final String SECRET_KEY = "7mGP4KpxqhDkBsze6YTAXth22fwQxWm3";

    public final static String path = System.getProperty("user.dir") + "/src/main/resources/static/test.jpg";

    //和Autowired一样都是做依赖注入的
    @Resource
    private ImgMsgMapper imgMsgMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    // 发送给Broker，默认会自动创建topic，topic和tag用冒号分隔
    @Override
    public ApiResult getMsgFromImg(MultipartFile multipartFile) {
        ApiResult apiResult = new ApiResult();
        boolean flag = saveImg(multipartFile);
        if (!flag) {
            apiResult.setCode(500);
            apiResult.setMsg("图片存入本地失败");
        }
        String result = ocr();
        if (result.isEmpty()) {
            apiResult.setCode(500);
            apiResult.setMsg("图片提取文字失败");
        }

        // 发送发邮件的消息
        rocketMQTemplate.convertAndSend("rocket-topic-2", new MQMessage(1, result));

        // 发送存数据库的消息
        rocketMQTemplate.convertAndSend("rocket-topic-2", new MQMessage(2, result));


//        String base64 = img2Base64();
//        if (base64.isEmpty()) {
//            apiResult.setCode(500);
//            apiResult.setMsg("图片转为base64失败");
//            System.out.println("转成base64失败");
//        }
//        Date time = new Date();
//        ImgMsg imgMsg = new ImgMsg();
//        imgMsg.setImg2base64(base64);
//        imgMsg.setImgResult(result);
//        imgMsg.setTime(time);
//        boolean saveImgMsg = saveImgMsg(imgMsg);
//        if (!saveImgMsg) {
//            apiResult.setCode(500);
//            apiResult.setMsg("图片存入数据库失败");
//            System.out.println("存入数据库失败");
//        }
        apiResult.setCode(200);
        apiResult.setData(result);
        return apiResult;
    }

    @Override
    public boolean saveImg(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return false;
        }
        File dest = new File(path);
        if (!dest.getParentFile().exists()) { //若dest的父级目录不存在
            dest.getParentFile().mkdir(); //新建文件夹
        }
        try {
            multipartFile.transferTo(dest); //保存文件
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String ocr() {
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // String path = "test.jpg";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
        JSONArray arr = res.getJSONArray("words_result");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length(); i++) {
            sb.append(arr.getJSONObject(i).getString("words"));
        }
        return sb.toString();
    }

    @Override
    public String img2Base64() {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(path);
            System.out.println("文件大小（字节）=" + in.available());
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组进行Base64编码，得到Base64编码的字符串
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str = encoder.encode(data);
        //System.out.println(base64Str);
        return base64Str;

    }

    //存数据库
    @Override
    public boolean saveImgMsg(ImgMsg imgMsg) {

//        Date time = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

//        System.out.println(time);
//        imgMsgMapper.saveResult(imgMsg);
        boolean flag = imgMsgMapper.saveResult(imgMsg);
        if (!flag) {
            return false;
        }
        return true;
    }
}
