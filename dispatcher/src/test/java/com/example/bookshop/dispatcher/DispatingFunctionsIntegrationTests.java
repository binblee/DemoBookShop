package com.example.bookshop.dispatcher;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FunctionalSpringBootTest
public class DispatingFunctionsIntegrationTests {

    @Autowired
    private FunctionCatalog functionCatalog;

    @Test
    void packOrder() {
        Function<OrderAcceptedMessage, Long> pack = functionCatalog.lookup(Function.class,"pack");
        long orderId = 120L;
        assertThat(pack.apply(new OrderAcceptedMessage(orderId))).isEqualTo(orderId);
    }

    @Test
	void labelOrder() {
		Function<Flux<Long>, Flux<OrderDispatchedMessage>> label = functionCatalog.lookup(Function.class, "label");
		Flux<Long> orderId = Flux.just(121L);

		StepVerifier.create(label.apply(orderId))
				.expectNextMatches(dispatchedOrder ->
						dispatchedOrder.equals(new OrderDispatchedMessage(121L)))
				.verifyComplete();
	}

    @Test
    void packAndLabelOrder(){
        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel 
            = functionCatalog.lookup("pack|label");
        long orderId = 120L;
        StepVerifier.create(packAndLabel.apply(new OrderAcceptedMessage(orderId)))
            .expectNextMatches(dispatchedOrder -> dispatchedOrder.equals(new OrderDispatchedMessage(orderId)))
            .verifyComplete();
    }

}
