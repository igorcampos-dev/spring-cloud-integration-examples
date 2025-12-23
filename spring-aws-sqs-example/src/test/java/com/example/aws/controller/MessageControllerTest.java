package com.example.aws.controller;

import com.example.aws.dto.SqsRequestDto;
import com.example.aws.exception.BusinessException;
import com.example.aws.service.SqsService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    private static final String request = Instancio.of(SqsRequestDto.class)
                                                   .as( obj -> new ObjectMapper().writeValueAsString(obj));

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SqsService service;

    @Test
    @DisplayName("Should publish message and return success response")
    void shouldPublishMessageSuccessfully() throws Exception {

        mockMvc.perform(post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value("Message published successfully"));

        verify(service).sendMessage(any(SqsRequestDto.class));
    }

    @Test
    @DisplayName("Should return 500 when service throws exception")
    void shouldReturnInternalServerErrorWhenServiceFails() throws Exception {
        doThrow(new BusinessException("SQS error", new RuntimeException("generic error")))
                .when(service).sendMessage(any(SqsRequestDto.class));

        mockMvc.perform(post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());

        verify(service).sendMessage(any(SqsRequestDto.class));
    }

}
