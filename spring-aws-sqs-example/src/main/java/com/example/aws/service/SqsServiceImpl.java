package com.example.aws.service;

import com.example.aws.dto.SqsRequestDto;
import com.example.aws.mapper.SqsMapper;
import com.example.aws.messaging.producer.SqsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsServiceImpl implements SqsService {

    private final SqsProducer producer;

    @Override
    public void sendMessage(SqsRequestDto dto) {
        producer.sendMessage(SqsMapper.toProducerDto(dto));
    }

}
