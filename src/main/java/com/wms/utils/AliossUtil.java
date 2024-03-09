package com.wms.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/*
* 上传云端存储工具
* */
@Slf4j
//@Component//既不属于controller也不属于service也不是数据访问，使用此注解加入IOC管理
public class AliossUtil {

    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    private String bucketName = "zwms-public";

    public AliossUtil() throws ClientException, com.aliyuncs.exceptions.ClientException {
    }

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = null;
        if (originalFilename != null) {
            fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint,credentialsProvider);
        ossClient.putObject(bucketName, fileName, inputStream);

        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }

    public boolean deleteFile(String Objectname){
        // 判断文件
        if (StringUtils.isNotEmpty(Objectname)) {
            OSS client = new OSSClientBuilder().build(endpoint,credentialsProvider);
            try {
                if (!client.doesBucketExist(bucketName)){
                    log.info("Bucket不存在");
                    return false;
                }
                client.deleteObject(bucketName, Objectname);
                log.info("<----------OSS文件删-------->\n" + Objectname);
            }catch (Exception e){
                e.printStackTrace();
                log.info("删除失败");
                return false;
            }finally {
                if (client != null) {
                    client.shutdown();
                }
            }
        }
        return true;
    }
}