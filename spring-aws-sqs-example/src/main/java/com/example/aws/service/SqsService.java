package com.example.aws.service;

import com.example.aws.dto.SqsRequestDto;

public interface SqsService {
    void sendMessage(SqsRequestDto dto);
}
