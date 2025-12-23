package com.example.aws.service;

import com.example.aws.dto.SqsProducerDto;
import com.example.aws.dto.SqsRequestDto;
import com.example.aws.messaging.producer.SqsProducer;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SqsServiceImplTest {

    private static final SqsRequestDto dto = Instancio.create(SqsRequestDto.class);

    @Mock
    private SqsProducer producer;

    @InjectMocks
    private SqsServiceImpl service;

    @Test
    @DisplayName("Should delegate message sending to SqsProducer")
    void shouldDelegateMessageSendingToProducer() {
        assertDoesNotThrow(() -> service.sendMessage(dto));
        verify(producer).sendMessage(any(SqsProducerDto.class));
    }

}
