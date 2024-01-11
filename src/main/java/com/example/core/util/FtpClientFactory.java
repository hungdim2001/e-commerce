package com.example.core.util;

import com.example.core.configuration.FtpClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

@Slf4j
public class FtpClientFactory extends BasePooledObjectFactory<FTPClient> {

    private FtpClientProperties config;

    public FtpClientFactory(FtpClientProperties config) {
        this.config = config;
    }

    @Override
    public FTPClient create() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding(config.getEncoding());
        ftpClient.setDataTimeout(config.getDataTimeout());
        ftpClient.setConnectTimeout(config.getConnectTimeout());
        ftpClient.setControlKeepAliveTimeout(config.getKeepAliveTimeout());

        try {

            ftpClient.connect(config.getHost(), config.getPort());
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                log.warn("ftpServer refused connection,replyCode:{}", replyCode);
                return null;
            }

            if (!ftpClient.login(config.getUsername(), config.getPassword())) {
                log.warn("ftpClient login failed... username is {}; password: {}", config.getUsername(), config.getPassword());
            }

            ftpClient.setBufferSize(config.getBufferSize());
            ftpClient.setFileType(config.getTransferFileType());
            if (config.isPassiveMode()) {
                ftpClient.enterLocalPassiveMode();
            }

        } catch (IOException e) {
            log.error("create ftp connection failed...", e);
        }
        return ftpClient;
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
        return new DefaultPooledObject<>(ftpClient);
    }
    @Override
    public void destroyObject(PooledObject<FTPClient> ftpPooled) {
        if (ftpPooled == null) {
            return;
        }

        FTPClient ftpClient = ftpPooled.getObject();

        try {
            ftpClient.logout();
        } catch (IOException io) {
            log.error("ftp client logout failed...{}", io);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException io) {
                log.error("close ftp client failed...{}", io);
            }
        }
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> ftpPooled) {
        try {
            FTPClient ftpClient = ftpPooled.getObject();
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            log.error("failed to validate client: {}", e);
        }
        return false;
    }


}
