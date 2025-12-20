package com.example.aws.messaging.producer;

import com.example.aws.dto.SqsProducerDto;
import com.example.aws.exception.BusinessException;
import com.example.aws.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsProducer {

    private final SqsClient sqsClient;
    private final AwsProperties properties;
    private final ObjectMapper mapper;

    public void sendMessage(SqsProducerDto message) {
        log.debug("Sending message to SQS. payload={}", message);
        try {
            String body = mapper.writeValueAsString(message);
            var response = sqsClient.sendMessage(builder -> builder
                                    .queueUrl(properties.getQueueExampleUrl())
                                    .messageBody(body));
            log.info("Message sent to SQS successfully. messageId={}", response.messageId());
        } catch (Exception e) {
            log.error("Error sending message to SQS. payload={}", message, e);
            throw new BusinessException("Failed to send message to SQS", e);
        }
    }


}
