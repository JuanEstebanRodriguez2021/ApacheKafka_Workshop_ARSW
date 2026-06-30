package edu.eci.lab3arsw.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic ordersTopic() {
        return new NewTopic("orders", 3, (short) 1);
    }
    @Bean
    public NewTopic paymentsTopic() {
        return new NewTopic("payments", 3, (short) 1);
    }
    @Bean
    public NewTopic inventoryTopic() {
        return new NewTopic("inventory", 3, (short) 1);
    }
}