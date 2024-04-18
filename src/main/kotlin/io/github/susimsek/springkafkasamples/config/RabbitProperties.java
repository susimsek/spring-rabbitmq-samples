package io.github.susimsek.springkafkasamples.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("spring.rabbitmq")
@Getter
@Setter
public class RabbitProperties {
    private Map<String, Binding> bindings;

    @Getter
    @Setter
    public static class Binding {
        private String queue;
        private String exchange;
        private String routingKey;
        private String dlq;
        private int delayInMs;
    }
}
