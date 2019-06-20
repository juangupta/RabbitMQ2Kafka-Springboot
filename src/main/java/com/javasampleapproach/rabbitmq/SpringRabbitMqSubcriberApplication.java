package com.javasampleapproach.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@SpringBootApplication
public class SpringRabbitMqSubcriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRabbitMqSubcriberApplication.class, args);
	}
	
	@Value("${kafka.bootstrap-servers}")
	  private String bootstrapServers;

	@Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          bootstrapServers);
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
 
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
	
	@Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        //connectionFactory.setUri("amqp://juagomez:vinula@localhost:5672");
    	//*******RabbitMQ-DEV
        //connectionFactory.setUri("amqps://admin:SNMYSCACWOLMHZHS@portal-ssl1233-20.bmix-dal-yp-eccd01e7-4f3d-4c90-bc67-220feeeb8e46.2126222060.composedb.com:54907/bmix-dal-yp-eccd01e7-4f3d-4c90-bc67-220feeeb8e46");
        connectionFactory.setUri("amqp://ifyrtjsc:F9fttMFgJ1_RUJX6d4DIvn9TyQiNmiR8@chimpanzee.rmq.cloudamqp.com/ifyrtjsc");
        //connectionFactory.setCacheMode(CacheMode.CONNECTION);
        //connectionFactory.setUsername("guest");
        //connectionFactory.setPassword("guest");
        return connectionFactory;
    }
	
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
	    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	    factory.setConnectionFactory(connectionFactory());
	    factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
	    factory.setConcurrentConsumers(10);
	    //factory.setMaxConcurrentConsumers(1000);
	    return factory;
	}
	
}
