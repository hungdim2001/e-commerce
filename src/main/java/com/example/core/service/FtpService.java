package com.example.core.service;

import com.amazonaws.util.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FtpService implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {
    @Value("${ftp.host}")
    private  String SERVER_ADDRESS ;
    @Value("${ftp.port}")
    private  int SERVER_PORT;
    @Value("${ftp.timeout}")
    private  int FTP_TIMEOUT ;
    @Value("${ftp.username}")
    private  String USERNAME ;
    @Value("${ftp.password}")
    private String PASSWORD ;
    private FTPClient ftpClient;
    private int reply;
    String ftpPath;

    public void uploadFile(MultipartFile file, String fileName) throws IOException {
        connectFTPServer();
        System.out.println("ftpPath: " + ftpPath + fileName);
        InputStream inputStream = file.getInputStream();
        boolean success = ftpClient.storeFile(fileName, inputStream);
    }

    public byte[] retrieveFile(String fileName) throws IOException {
        connectFTPServer();
        System.out.println("ftpPath: " + ftpPath + fileName);
        InputStream inputStream = ftpClient.retrieveFileStream(ftpPath + fileName);
        System.out.println("input Stream" + inputStream);
        byte[] fileContent = IOUtils.toByteArray(inputStream);
        return fileContent;
    }

    private void connectFTPServer() throws IOException {
        ftpClient = new FTPClient();
        try {
            System.out.println("connecting ftp server...");
            ftpClient.connect(SERVER_ADDRESS, SERVER_PORT);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("FTP server not respond!");
            } else {
                System.out.println("connected");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpPath = ftpClient.printWorkingDirectory();
                ftpClient.setDataTimeout(FTP_TIMEOUT);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        disconnectFTPServer();
    }

    @Override
    public void run(String... args) throws Exception {
        connectFTPServer();
    }

    private void disconnectFTPServer() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}