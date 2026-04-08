package com.soc.backend_core;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic networkEventsTopic() {
        return TopicBuilder.name("events.network")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic endpointEventsTopic() {
        return TopicBuilder.name("events.endpoint")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic alertsTopic() {
        return TopicBuilder.name("events.alerts")
                .partitions(1)
                .replicas(1)
                .build();
    }
}