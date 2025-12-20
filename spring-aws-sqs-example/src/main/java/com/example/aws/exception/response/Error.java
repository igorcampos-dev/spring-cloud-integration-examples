package com.example.aws.exception.response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String timestamp;
    private String path;
    private Integer status;
    private String error;

    public static ResponseEntity<@NonNull Error> response(String message, HttpStatus status, HttpServletRequest request){
        return ResponseEntity
                .status(status)
                .body(Error.builder()
                           .timestamp(getInstantNow())
                           .path(request.getRequestURI())
                           .status(status.value())
                           .error(message)
                           .build());
    }

    private static String getInstantNow() {
        return Instant.now()
                      .atZone(ZoneId.systemDefault())
                      .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

    }

}