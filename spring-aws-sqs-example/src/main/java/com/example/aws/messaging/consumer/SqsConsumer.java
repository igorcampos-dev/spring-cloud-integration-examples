package com.example.aws.messaging.consumer;

import com.example.aws.dto.SqsConsumerDto;
import com.example.aws.exception.BusinessException;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsConsumer {

    private final ObjectMapper mapper;

    @SqsListener("${aws.sqs.queueExampleName}")
    public void receiveMessage(String message) {
        log.debug("Raw message received: {}", message);
        try {
            SqsConsumerDto dto = mapper.readValue(message, SqsConsumerDto.class);
            log.info("Processing message: message={}", dto.getMessage());
        } catch (Exception e) {
            String errorMessage = "Error processing SQS message. rawMessage= " + message;
            log.error(errorMessage, e);
            throw new BusinessException(errorMessage, e);
        }
    }

}
