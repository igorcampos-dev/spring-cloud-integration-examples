package com.example.aws.mapper;

import com.example.aws.dto.SqsProducerDto;
import com.example.aws.dto.SqsRequestDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SqsMapperTest {

    private static final SqsRequestDto dto = Instancio.create(SqsRequestDto.class);

    @Test
    @DisplayName("Should map SqsRequestDto to SqsProducerDto")
    void shouldMapRequestDtoToProducerDto() {
        SqsProducerDto result = SqsMapper.toProducerDto(dto);
        assertNotNull(result);
        assertEquals(dto.getMessage(), result.getMessage());
    }
}
