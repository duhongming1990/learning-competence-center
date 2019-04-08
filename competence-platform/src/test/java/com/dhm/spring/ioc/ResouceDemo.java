package com.dhm.spring.ioc;

import org.springframework.core.io.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2019/1/29 18:23
 */
public class ResouceDemo {
    public static void main(String[] args) throws IOException {

        //文件系统资源
        Resource fileSystemResource = new FileSystemResource("D:\\IdeaProjects\\amazing_project\\learning-competence-center\\competence-platform\\src\\test\\resources\\application.properties");
        InputStream fileSystemInputStream = fileSystemResource.getInputStream();

        //类路径资源
        Resource classPathResource = new ClassPathResource("application.properties");
        InputStream classPathInputStream = classPathResource.getInputStream();

        //URL资源
        Resource urlResource = new UrlResource("https://www.baidu.com");
        InputStream urlInputStream = urlResource.getInputStream();

        //字节数组资源
        byte[] bytes = {};
        Resource byteArrayResource = new ByteArrayResource(bytes);
        InputStream byteArrayInputStream = byteArrayResource.getInputStream();

    }
}