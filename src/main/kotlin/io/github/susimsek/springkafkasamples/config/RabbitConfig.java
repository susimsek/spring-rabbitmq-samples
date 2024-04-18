package io.github.susimsek.springkafkasamples.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateCustomizer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitConfig {

    private static final String CUSTOMER_BINDING_NAME = "customer";

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
        SimpleRabbitListenerContainerFactoryConfigurer configurer,
        ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer,
                                         ConnectionFactory connectionFactory,
                                         ObjectProvider<RabbitTemplateCustomizer> customizers) {
        RabbitTemplate template = new RabbitTemplate();
        configurer.configure(template, connectionFactory);
        customizers.orderedStream().forEach(customizer -> customizer.customize(template));
        return template;
    }

    @Bean
    public Queue customerQueue(
        Binding customerDeadLetterBinding,
        RabbitProperties rabbitProperties) {
        var binding = rabbitProperties.getBindings().get(CUSTOMER_BINDING_NAME);
        return QueueBuilder.durable(binding.getQueue())
            .deadLetterExchange(customerDeadLetterBinding.getExchange())
            .deadLetterRoutingKey(customerDeadLetterBinding.getRoutingKey())
            .ttl(binding.getDelayInMs())
            .build();
    }

    @Bean
    Queue customerDeadLetterQueue(RabbitProperties rabbitProperties) {
        var binding = rabbitProperties.getBindings().get(CUSTOMER_BINDING_NAME);
        return new Queue(binding.getDlq());
    }

    @Bean
    TopicExchange customerExchange(RabbitProperties rabbitProperties) {
        var binding = rabbitProperties.getBindings().get(CUSTOMER_BINDING_NAME);
        return new TopicExchange(binding.getExchange());
    }

    @Bean
    Binding customerBinding(Queue customerQueue,
                            TopicExchange customerExchange,
                            RabbitProperties rabbitProperties) {
        var binding = rabbitProperties.getBindings().get(CUSTOMER_BINDING_NAME);
        return BindingBuilder.bind(customerQueue).to(customerExchange)
            .with(binding.getRoutingKey());
    }

    @Bean
    Binding customerDeadLetterBinding(
        Queue customerDeadLetterQueue,
        TopicExchange customerExchange) {
        return BindingBuilder.bind(customerDeadLetterQueue).
            to(customerExchange)
            .with(customerDeadLetterQueue.getName());
    }
}
