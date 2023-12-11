package com.example.core.service;

import com.amazonaws.util.IOUtils;
import com.example.core.util.FTPUtils;
import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;

@Service
public class FtpService {
    @Value("${ftp.remoteDirPath}")
    String remoteDirPath; // Đường dẫn tới thư mục trên FTP
    @Autowired(required = false)
    FTPUtils ftpUtils;

    public boolean uploadFile(String folder, MultipartFile file, String fileName) throws Exception {
        FTPClient ftpClient = ftpUtils.getFtpClient();
        boolean successChangeWorkingDirectory = ftpClient.changeWorkingDirectory(remoteDirPath + folder);
        if (!successChangeWorkingDirectory) {
            // Directory doesn't exist, try creating it
            boolean created = makeDirectories(ftpClient,  folder);
            if (created) {
                System.out.println("Directory created successfully: " + remoteDirPath + folder);
                ftpClient.changeWorkingDirectory(remoteDirPath + folder); // Change to newly created directory
            } else {
                System.out.println("Failed to create directory: " + remoteDirPath + folder);
            }
        } else {
            System.out.println("Directory exists: " + remoteDirPath + folder);
        }
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        InputStream inputStream = file.getInputStream();
        boolean successUpload = ftpClient.storeFile(fileName, inputStream);
        return successUpload;
    }

    public boolean deleteFile(String folder, String fileName) throws Exception {
        FTPClient ftpClient = ftpUtils.getFtpClient();
        boolean successChangeWorkingDirectory = ftpClient.changeWorkingDirectory(remoteDirPath + folder);
        if (!successChangeWorkingDirectory) {
            // Directory doesn't exist, try creating it
//            boolean created = ftpClient.makeDirectory(remoteDirPath + folder);
//
//            boolean created = makeDirectories(ftpClient, remoteDirPath + folder);
//
//            if (created) {
//                System.out.println("Directory created successfully: " + remoteDirPath + folder);
//                ftpClient.changeWorkingDirectory(remoteDirPath + folder); // Change to newly created directory
//            } else {
//                System.out.println("Failed to create directory: " + remoteDirPath + folder);
//            }
            throw new IOException("File does not exist: " + folder+fileName);

        } else {
            System.out.println("Directory exists: " + remoteDirPath + folder);
        }
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        boolean deleteSuccess = ftpClient.deleteFile(fileName);
        return deleteSuccess;
    }

    public byte[] retrieveFile(String folder, String fileName) throws Exception {
        FTPClient ftpClient = ftpUtils.getFtpClient();
        boolean successChangeWorkingDirectory = ftpClient.changeWorkingDirectory(remoteDirPath + folder);
        if (!successChangeWorkingDirectory) {
            throw new IOException("Folder does not exist: " + folder);
        }

        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        InputStream inputStream = ftpClient.retrieveFileStream(fileName);
        if (inputStream == null) {
            throw new IOException("Could not retrieve file: " + fileName);
        }

        byte[] fileContent;
        try {
            fileContent = IOUtils.toByteArray(inputStream);
        } finally {
            inputStream.close(); // Đóng luồng sau khi sử dụng
            ftpClient.completePendingCommand(); // Hoàn tất lệnh FTP trước khi ngắt kết nối
        }

        return fileContent;
    }

    public static boolean makeDirectories(FTPClient ftpClient, String dirPath)
            throws IOException {
        String[] pathElements = dirPath.split("/");
        if (pathElements != null) {
            for (String singleDir : pathElements) {
                boolean existed = ftpClient.changeWorkingDirectory(singleDir);
                if (!existed) {
                    boolean created = ftpClient.makeDirectory(singleDir);
                    if (created) {
                        System.out.println("CREATED directory: " + singleDir);
                        ftpClient.changeWorkingDirectory(singleDir);
                    } else {
                        System.out.println("COULD NOT create directory: " + singleDir);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}


