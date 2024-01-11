package com.example.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.net.ftp.FTP;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Getter
@Setter
@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "ftp.client")
public class FtpClientProperties {
    private String host;

    private Integer port = 21;

    private String username;

    private String password;

    private boolean passiveMode = false;

    private String encoding = "UTF-8";

    private Integer connectTimeout;

    private Integer dataTimeout;

    private Integer bufferSize = 1024;

    private Integer keepAliveTimeout = 0;

    private Integer transferFileType = FTP.ASCII_FILE_TYPE;
}
