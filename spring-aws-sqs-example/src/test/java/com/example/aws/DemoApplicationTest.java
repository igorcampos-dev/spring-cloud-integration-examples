package com.example.aws;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.aws.sqs.enabled=false"
})
class DemoApplicationTest {

    @Test
    @DisplayName("Should load Spring application context")
    void shouldLoadApplicationContext() {
    }

}
