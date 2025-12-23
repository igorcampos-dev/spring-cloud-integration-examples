package com.example.aws.messaging.consumer;

import com.example.aws.exception.BusinessException;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

class SqsConsumerTest {

    private static final String message = Instancio.of(SqsConsumer.class)
                                                   .as( obj -> new ObjectMapper().writeValueAsString(obj));
    private static final String invalidMessage = Instancio.create(String.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final SqsConsumer consumer = new SqsConsumer(mapper);

    @Test
    @DisplayName("Should process a valid JSON message without throwing exception")
    void shouldProcessValidMessage() {
        assertDoesNotThrow(() -> consumer.receiveMessage(message));
    }

    @Test
    @DisplayName("Should throw BusinessException when message is invalid JSON")
    void shouldThrowBusinessExceptionWhenMessageIsInvalid() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> consumer.receiveMessage(invalidMessage)
        );
        assertTrue(exception.getMessage().contains("Error processing SQS message"));
    }

}
