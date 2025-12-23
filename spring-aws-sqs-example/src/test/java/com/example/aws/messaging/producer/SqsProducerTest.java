package com.example.aws.messaging.producer;

import com.example.aws.dto.SqsProducerDto;
import com.example.aws.exception.BusinessException;
import com.example.aws.properties.AwsProperties;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import tools.jackson.databind.ObjectMapper;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SqsProducerTest {

    SqsProducerDto dto;

    @Mock
    private SqsClient sqsClient;

    @Mock
    private AwsProperties properties;
    private SqsProducer producer;

    @BeforeEach
    void setup() {
        producer = new SqsProducer(sqsClient, properties, new ObjectMapper());
        dto = Instancio.create(SqsProducerDto.class);
    }

    @Test
    @DisplayName("Should send message successfully to SQS")
    void shouldSendMessageSuccessfully() {
        when(sqsClient.sendMessage(ArgumentMatchers.<Consumer<SendMessageRequest.Builder>>any()))
                .thenAnswer(invocation -> {
                    invocation.<Consumer<SendMessageRequest.Builder>>getArgument(0)
                              .accept(SendMessageRequest.builder());
                    return SendMessageResponse.builder()
                            .messageId("msg-123")
                            .build();
                });

        assertDoesNotThrow(() -> producer.sendMessage(dto));

        verify(sqsClient).sendMessage(ArgumentMatchers.<Consumer<SendMessageRequest.Builder>>any());
    }

    @Test
    @DisplayName("Should throw BusinessException when SQS client fails")
    void shouldThrowBusinessExceptionWhenSqsFails() {
        when(sqsClient.sendMessage(ArgumentMatchers.<Consumer<SendMessageRequest.Builder>>any()))
                      .thenThrow(new RuntimeException("SQS down"));

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> producer.sendMessage(dto)
        );

        assertEquals("Failed to send message to SQS", ex.getMessage());
    }

}