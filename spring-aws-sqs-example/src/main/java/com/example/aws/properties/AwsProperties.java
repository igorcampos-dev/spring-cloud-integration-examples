package com.example.aws.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String accessKey;
    private String secretKey;
    private String region;
    private String host;
    private Sqs sqs = new Sqs();

    @Data
    static class Sqs {
        private String queueExampleUrl;
    }

    public String getQueueExampleUrl() {
        return this.sqs.getQueueExampleUrl();
    }

}
