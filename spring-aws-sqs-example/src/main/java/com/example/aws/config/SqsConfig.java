package com.example.aws.config;

import com.example.aws.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class SqsConfig {

    private final AwsProperties properties;

    @Bean
    public AwsBasicCredentials awsBasicCredentials(){
        return AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey());
    }

    @Bean
    public SqsClient sqsClient(AwsBasicCredentials credentials) {
        return SqsClient.builder()
                .region(Region.of(properties.getRegion()))
                .endpointOverride(URI.create(properties.getHost()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient(AwsBasicCredentials credentials) {
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create(properties.getHost()))
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}