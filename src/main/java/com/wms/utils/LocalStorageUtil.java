package com.wms.utils;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class LocalStorageUtil {

    //上传到本地的路径
    @Value("${demo.web.upload-path}")
    private String uploadPath;

    public String upload(MultipartFile file, HttpServletRequest request){
        //处理文件参数为空
        if (file == null) {
            throw new RuntimeException("文件为空");
        }

        // 在 uploadPath 文件夹中通过日期对上传的文件归类保存
        // 例如：/2022/02/22/df9a66f1-760b-4f95-9faf-b5a216966718.png
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM/dd");
        String format = sdf.format(new Date());
        File folder = new File(uploadPath + format);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        // 对上传的文件重命名, 避免文件重名
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString()
                + oldName.substring(oldName.lastIndexOf("."), oldName.length());

        try {
            // 文件保存
            file.transferTo(new File(folder, newName));
            // 添加日志输出
            log.info("文件保存成功：" + folder.getPath() + File.separator + newName);
            // 返回上传文件的访问路径
            // 例如：http://localhost:9999/2022/02/22/df9a66f1-760b-4f95-9faf-b5a216966718.png
            String filePath = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath() + "/" + format +"/"+ newName;

            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("系统错误");
        }
    }

    public boolean delete(String fileUrl)  {
        //使用Url工具类来分离路径
        URI uri = null;
        try {
            uri = new URI(fileUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String path = uri.getRawPath();

        //合并路径
        //去除“/”
        String upPath =uploadPath.replaceFirst("/$","");
        String filepath=upPath+path;

        //删除文件
        File file =new File(filepath);
        if(file.exists()){
            return file.delete();
        }else{
            return true;
        }
    }
}
