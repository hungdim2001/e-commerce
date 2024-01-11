package com.example.core.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.SocketException;

@Configuration
@EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
public class FTPConfig {

    @Value("${ftp.client.host}")
    private String serverAddress;

    @Value("${ftp.client.port}")
    private int serverPort;

    @Value("${ftp.client.username}")
    private String username;

    @Value("${ftp.client.password}")
    private String password;

    @Bean
    public GenericObjectPool<FTPClient> ftpClientPool() {
        GenericObjectPoolConfig<FTPClient> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(80); // Điều chỉnh số kết nối tối đa trong pool
        poolConfig.setMaxIdle(100);
        return new GenericObjectPool<>(new FTPClientFactory(serverAddress, serverPort, username, password), poolConfig);
    }

    private static class FTPClientFactory extends BasePooledObjectFactory<FTPClient> {
        private final String serverAddress;
        private final int serverPort;
        private final String username;
        private final String password;

        public FTPClientFactory(String serverAddress, int serverPort, String username, String password) {
            Assert.notNull(serverAddress, "Server address must not be null");
            this.serverAddress = serverAddress;
            this.serverPort = serverPort;
            this.username = username;
            this.password = password;
        }

        @Override
        public FTPClient create() {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(serverAddress, serverPort);
                int reply = ftpClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    throw new IOException("FTP server not respond!");
                }
                ftpClient.login(username, password);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ftpClient;
        }

        @Override
        public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
            return new DefaultPooledObject<>(ftpClient);
        }

        @Override
        public void destroyObject(PooledObject<FTPClient> p) {
            try {
                FTPClient ftpClient = p.getObject();
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
