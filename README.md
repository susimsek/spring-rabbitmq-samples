# Spring Rabbitmq Samples
Rabbitmq Samples using Spring Boot

<img src="https://github.com/susimsek/spring-rabbitmq-samples/blob/main/images/introduction.png" alt="Spring Boot Rabbitmq Samples" width="100%" height="100%"/> 

# Rabbitmq

Spring AMQP comprises two modules: spring-amqp and spring-rabbit. Together, these modules provide abstractions for:

AMQP entities – we create entities with the Message, Queue, Binding, and Exchange classes
Connection Management – we connect to our RabbitMQ broker by using a CachingConnectionFactory
Message Publishing – we use a RabbitTemplate to send messages
Message Consumption – we use a @RabbitListener to read messages from a queue

## Prerequisites

* Java 17
* Kotlin
* Maven 3.x
* Rabbitmq


## Build

You can install the dependencies and build by typing the following command

```sh
mvn clean install
```

## Testing

You can run application's tests by typing the following command

```
mvn verify
```


## Code Quality

You can test code quality locally via sonarqube by typing the following command

```sh
mvn -Psonar compile initialize sonar:sonar
```

## Detekt

Detekt a static code analysis tool for the Kotlin programming language

You can run detekt by typing the following command

```sh
mvn antrun:run@detekt
```

## Docker

You can also fully dockerize  the sample applications. To achieve this, first build a docker image of your app.
The docker image of sample app can be built as follows:


```sh
mvn verify jib:dockerBuild
```

# Used Technologies
* Java 17
* Kotlin
* Docker
* Sonarqube
* Detekt
* Checkstyle
* Rabbitmq
* Spring Boot 3.x
* Spring Boot Amqp
* Spring Boot Web
* Spring Boot Validation
* Spring Boot Actuator
* Lombok
* Springdoc
