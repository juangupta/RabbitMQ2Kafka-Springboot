package com.javasampleapproach.rabbitmq.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


@Component
public class Sender {
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate2;

	private static final String TOPIC = "test";
    
    public ListenableFuture<SendResult<String, String>> send(String message) {
        return this.kafkaTemplate2.send(TOPIC, message);
        
    }
 
}


