package com.example.aws.controller;

import com.example.aws.dto.SqsRequestDto;
import com.example.aws.dto.SqsResponseDto;
import com.example.aws.service.SqsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final SqsService service;

    @PostMapping
    public ResponseEntity<@NonNull SqsResponseDto> publishMessage(@RequestBody SqsRequestDto message) {
        service.sendMessage(message);
        return ResponseEntity.ok(new SqsResponseDto("Message published successfully"));
    }

}
