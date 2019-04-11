package com.taotao.controller;

import com.taotao.common.pojo.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传Controller
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map uploadPic(MultipartFile uploadFile) {
        try {
            // 首先接收页面上传的文件
            byte[] content = uploadFile.getBytes();
            // 取出文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 把这个文件内容上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/client.conf");
            String url = fastDFSClient.uploadFile(content, ext);
            // 从配置文件中取图片服务器的url
            // 创建返回结果对象
            Map result = new HashMap();
            String URL = IMAGE_SERVER_BASE_URL + url;
            System.out.println(URL);
            result.put("error", 0);
            result.put("url", URL);
            // 返回结果
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return result;
        }
    }
}
