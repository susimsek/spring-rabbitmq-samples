package io.github.susimsek.springkafkasamples.consumer;

import io.github.susimsek.springkafkasamples.dto.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventCustomer {

    @RabbitListener(queues = "${spring.rabbitmq.bindings.customer.dlq}")
    public void consumeDlq(Customer payload) {
        log.info("customer event consumer on dlq:  payload: {} ", payload.toString());
    }
}