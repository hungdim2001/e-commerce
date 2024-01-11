package com.example.core.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class FTPUtils {
    @Value("${ftp.client.host}")
    private String SERVER_ADDRESS;
    @Value("${ftp.client.port}")
    private int SERVER_PORT;
    @Value("${ftp.client.connectTimeout}")
    private int FTP_TIMEOUT;
    @Value("${ftp.client.username}")
    private String USERNAME;
    @Value("${ftp.client.password}")
    private String PASSWORD;
    @Value("${ftp.remoteDirPath}")
    String remoteDirPath; // Đường dẫn tới thư mục trên FTP

    public FTPClient getFtpClient() throws  Exception{

       FTPClient ftpClient = new FTPClient();
       int reply;
        try {
            System.out.println("connecting ftp server....");
            ftpClient.connect(SERVER_ADDRESS);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("FTP server not respond!");
            } else {
                boolean success = ftpClient.changeWorkingDirectory(remoteDirPath);
                if (!success) {
                    boolean created = ftpClient.makeDirectory(remoteDirPath);
                    if (created) {
                        System.out.println("Directory created successfully: " + remoteDirPath);
                        ftpClient.changeWorkingDirectory(remoteDirPath); // Change to newly created directory
                    } else {
                        System.out.println("Failed to create directory: " + remoteDirPath);
                    }
                } else {
                    System.out.println("Directory exists: " + remoteDirPath);
                }
                System.out.println("connected");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return  ftpClient;
    }
}
