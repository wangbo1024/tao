package com.taotao.test;

import com.taotao.utils.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyTest {
    public void show() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("192.168.67.128");
        ftpClient.login("ftpuser","ftpuser");
        ftpClient.enterLocalPassiveMode();
//        定义我们是在做上传图片
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        FileInputStream fis = new FileInputStream(new File("E:\\教学资料\\java\\图片资源\\2.jpg"));
        ftpClient.storeFile("/home/ftpuser/www/images/2.jpg",fis);
        fis.close();
        ftpClient.logout();
    }
    public void show1() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File("E:\\教学资料\\java\\图片资源\\1.jpg"));
        boolean b = FtpUtil.uploadFile("192.168.67.128",21,"ftpuser","ftpuser",
                "/home/ftpuser/www/images","2020/04/25","1.jpg",fis);
        System.out.println(b);
    }
}
