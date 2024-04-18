package io.github.susimsek.springkafkasamples.producer;

import io.github.susimsek.springkafkasamples.dto.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerEventProducer {

    private final RabbitTemplate rabbitTemplate;

    private final Binding customerBinding;

    public void publish(Customer customer) {
        rabbitTemplate.convertAndSend(
            customerBinding.getExchange(),
            customerBinding.getRoutingKey(),
            customer);
        log.info("customer event producer payload: {} ", customer);
    }
}
