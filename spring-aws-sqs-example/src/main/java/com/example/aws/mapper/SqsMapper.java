package com.example.aws.mapper;

import com.example.aws.dto.SqsProducerDto;
import com.example.aws.dto.SqsRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqsMapper {

    public static SqsProducerDto toProducerDto(SqsRequestDto dto){
        return new SqsProducerDto(dto.getMessage());
    }

}
