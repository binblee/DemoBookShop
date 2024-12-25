package com.example.bookshop.dispatcher;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class FunctionsStreamIntegrationTests {
    @Autowired
    private InputDestination inputDestination;

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenOrderAcceptedThenDispatched() throws IOException {
        long orderId = 121;
        Message<OrderAcceptedMessage> inputMessage = MessageBuilder
            .withPayload(new OrderAcceptedMessage(orderId))
            .build();
        Message<OrderDispatchedMessage> expectedOutputMessage = MessageBuilder
            .withPayload(new OrderDispatchedMessage(orderId))
            .build();
        this.inputDestination.send(expectedOutputMessage);
        assertThat(objectMapper.readValue(outputDestination.receive().getPayload(), OrderDispatchedMessage.class))
            .isEqualTo(expectedOutputMessage.getPayload());
    }
}
