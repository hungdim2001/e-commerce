package com.example.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;


@Slf4j

public class FtpClientTemplate {
    private FtpClientPool ftpClientPool;
    @Value("${ftp.remoteDirPath}")
    String remoteDirPath;
    public FtpClientTemplate(FtpClientFactory ftpClientFactory) throws Exception {
        this.ftpClientPool = new FtpClientPool(10,ftpClientFactory);
    }
    public boolean uploadFile(File localFile, String remotePath) {
        FTPClient ftpClient = null;
        BufferedInputStream inStream = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.warn("ftpServer refused connection, replyCode:{}", replyCode);
                return false;
            }
            ftpClient.changeWorkingDirectory(remotePath);
            inStream = new BufferedInputStream(new FileInputStream(localFile));
            log.info("start upload... {}", localFile.getName());

            final int retryTimes = 3;

            for (int j = 0; j <= retryTimes; j++) {
                boolean success = ftpClient.storeFile(localFile.getName(), inStream);
                if (success) {
                    log.info("upload file success! {}", localFile.getName());
                    return true;
                }
                log.warn("upload file failure! try uploading again... {} times", j);
            }

        } catch (FileNotFoundException e) {
            log.error("file not found!{}", localFile);
        } catch (Exception e) {
            log.error("upload file failure!", e);
        } finally {
            IOUtils.closeQuietly(inStream);
            ftpClientPool.returnObject(ftpClient);
        }
        return false;
    }

    public byte[] downloadFile(String folder, String fileName) {
        FTPClient ftpClient = null;
        InputStream inputStream = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.warn("ftpServer refused connection, replyCode:{}", replyCode);
                return null;
            }

            boolean successChangeWorkingDirectory = ftpClient.changeWorkingDirectory(remoteDirPath + folder);
            if (!successChangeWorkingDirectory) {
                throw new IOException("Folder does not exist: " + folder);
            }
            inputStream = ftpClient.retrieveFileStream(fileName);
            if (inputStream == null) {
                throw new IOException("Could not retrieve file: " + fileName);
            }
//            FTPFile[] ftpFiles = ftpClient.listFiles();
//            for (FTPFile file : ftpFiles) {
//                if (fileName.equalsIgnoreCase(file.getName())) {
//                    inputStream = ftpClient.retrieveFileStream(fileName);
//                    if (inputStream == null) {
//                        throw new IOException("Could not retrieve file: " + fileName);
//                    }
//                }
//            }
            ftpClient.logout();
            return com.amazonaws.util.IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            log.error("download file failure!", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            ftpClientPool.returnObject(ftpClient);
        }
        return null;
    }


    public boolean deleteFile(String remotePath, String fileName) {
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.warn("ftpServer refused connection, replyCode:{}", replyCode);
                return false;
            }
            ftpClient.changeWorkingDirectory(remotePath);
            int delCode = ftpClient.dele(fileName);
            log.debug("delete file reply code:{}", delCode);
            return true;
        } catch (Exception e) {
            log.error("delete file failure!", e);
        } finally {
            ftpClientPool.returnObject(ftpClient);
        }
        return false;
    }

}
