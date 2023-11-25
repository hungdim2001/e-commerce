package com.example.core.service;

import com.amazonaws.util.IOUtils;
import lombok.Data;
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
@Data
public class FtpService implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {
    @Value("${ftp.host}")
    private String SERVER_ADDRESS;
    @Value("${ftp.port}")
    private int SERVER_PORT;
    @Value("${ftp.timeout}")
    private int FTP_TIMEOUT;
    @Value("${ftp.username}")
    private String USERNAME;
    @Value("${ftp.password}")
    private String PASSWORD;
    @Value("${ftp.remoteDirPath}")
    String remoteDirPath; // Đường dẫn tới thư mục trên FTP

    private FTPClient ftpClient;
    private int reply;
    String ftpPath;

    public boolean uploadFile(String folder, MultipartFile file, String fileName) throws IOException {
        connectFTPServer();
        boolean successChangeWorkingDirectory = ftpClient.changeWorkingDirectory(remoteDirPath+folder);
        if (!successChangeWorkingDirectory) {
            // Directory doesn't exist, try creating it
            boolean created = ftpClient.makeDirectory(remoteDirPath+folder);
            if (created) {
                System.out.println("Directory created successfully: " + remoteDirPath+folder);
                ftpClient.changeWorkingDirectory(remoteDirPath+folder); // Change to newly created directory
            } else {
                System.out.println("Failed to create directory: " + remoteDirPath+folder);
            }
        } else {
            System.out.println("Directory exists: " + remoteDirPath+folder);
        }
        System.out.println("ftpPath: " + ftpPath + fileName);
        InputStream inputStream = file.getInputStream();
        boolean successUpload = ftpClient.storeFile(fileName, inputStream);
        disconnectFTPServer();
        return successUpload;
    }
    public boolean deleteFile(String folder, String fileName) throws IOException {
        connectFTPServer();
        boolean successChangeWorkingDirectory = ftpClient.changeWorkingDirectory(remoteDirPath+folder);
        if (!successChangeWorkingDirectory) {
            // Directory doesn't exist, try creating it
            boolean created = ftpClient.makeDirectory(remoteDirPath+folder);
            if (created) {
                System.out.println("Directory created successfully: " + remoteDirPath+folder);
                ftpClient.changeWorkingDirectory(remoteDirPath+folder); // Change to newly created directory
            } else {
                System.out.println("Failed to create directory: " + remoteDirPath+folder);
            }
        } else {
            System.out.println("Directory exists: " + remoteDirPath+folder);
        }
        boolean deleteSuccess = ftpClient.deleteFile(fileName);
        disconnectFTPServer();
        return deleteSuccess;
    }

    public byte[] retrieveFile(String folder,String fileName) throws IOException {
        connectFTPServer();
        boolean successChangeWorkingDirectory = ftpClient.changeWorkingDirectory(remoteDirPath+folder);
        if (!successChangeWorkingDirectory) {
            throw  new IOException("folder not exist" + folder);
        }
        System.out.println(ftpClient.printWorkingDirectory());
        InputStream inputStream = ftpClient.retrieveFileStream(fileName);
        System.out.println("input Stream" + inputStream);
        byte[] fileContent = IOUtils.toByteArray(inputStream);
        disconnectFTPServer();
        return fileContent;
    }

    private void connectFTPServer() throws IOException {
        ftpClient = new FTPClient();
        try {
            System.out.println("connecting ftp server...");
            ftpClient.connect(SERVER_ADDRESS, SERVER_PORT);
            ftpClient.login(USERNAME, PASSWORD);
            boolean success = ftpClient.changeWorkingDirectory(remoteDirPath);
            if (!success) {
                // Directory doesn't exist, try creating it
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
//        connectFTPServer();
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