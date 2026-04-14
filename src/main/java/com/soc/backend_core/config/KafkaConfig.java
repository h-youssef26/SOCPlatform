package com.soc.backend_core.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.soc.backend_core.Entities.domain.UnifiedEvent;

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



    @Bean
    public NewTopic loginEventsTopic() {
        return TopicBuilder.name("events.login")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
