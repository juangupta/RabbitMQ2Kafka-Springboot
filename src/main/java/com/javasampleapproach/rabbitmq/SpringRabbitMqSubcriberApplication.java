package com.javasampleapproach.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringRabbitMqSubcriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRabbitMqSubcriberApplication.class, args);
	}
	
	@Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        //connectionFactory.setUri("amqp://juagomez:vinula@localhost:5672");
    	//*******RabbitMQ-DEV
        //connectionFactory.setUri("amqps://admin:SNMYSCACWOLMHZHS@portal-ssl1233-20.bmix-dal-yp-eccd01e7-4f3d-4c90-bc67-220feeeb8e46.2126222060.composedb.com:54907/bmix-dal-yp-eccd01e7-4f3d-4c90-bc67-220feeeb8e46");
        connectionFactory.setUri("amqp://kqeniqmj:9IX8D-nhTOJRQo643vNuHeJwvwdb-NYw@fly.rmq.cloudamqp.com/kqeniqmj");
    	//connectionFactory.setCacheMode(CacheMode.CONNECTION);
        //connectionFactory.setUsername("guest");
        //connectionFactory.setPassword("guest");
        return connectionFactory;
    }
	
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
	    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	    factory.setConnectionFactory(connectionFactory());
	    //factory.setConcurrentConsumers(50);
	    //factory.setMaxConcurrentConsumers(1000);
	    return factory;
	}
}
