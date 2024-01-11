package com.example.core.configuration;

import com.example.core.util.FtpClientFactory;
import com.example.core.util.FtpClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FtpClientProperties.class)
public class FtpClientConfigure {
    private FtpClientProperties ftpClientProperties;

    @Autowired
    public void setFtpClientProperties(FtpClientProperties ftpClientProperties) {
        this.ftpClientProperties = ftpClientProperties;
    }

    @Bean
    public FtpClientFactory getFtpClientFactory() {
        return new FtpClientFactory(ftpClientProperties);
    }

    @Bean
    public FtpClientTemplate getFtpTemplate() throws Exception {
        return new FtpClientTemplate(getFtpClientFactory());
    }


}
