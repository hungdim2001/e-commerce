package com.example.core.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class FtpFileService {

    @Autowired
    private GenericObjectPool<FTPClient> ftpClientPool;

    public boolean uploadFile(String remoteDirPath, MultipartFile file) {
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            if (ftpClient != null) {
                String fileName = file.getOriginalFilename();
                try (InputStream inputStream = file.getInputStream()) {
                    return ftpClient.storeFile(remoteDirPath + fileName, inputStream);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient != null) {
                try {
                    ftpClientPool.returnObject(ftpClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public byte[] downloadFile(String remoteFilePath) {
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            if (ftpClient != null) {
                try (OutputStream outputStream = new ByteArrayOutputStream()) {
                    boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);
                    if (success) {
                        return ((ByteArrayOutputStream) outputStream).toByteArray();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient != null) {
                try {
                    ftpClientPool.returnObject(ftpClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
