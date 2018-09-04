package com.javasampleapproach.rabbitmq.subcriber;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Subcriber {

	@RabbitListener(queues="${jgg.rabbitmq.queue}", containerFactory="rabbitListenerContainerFactory")
    //public String recievedMessage(String msg) {
		public void recievedMessage(String msg) {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(new Timestamp(System.currentTimeMillis()) + " - Recieved Message: " + msg);
        //return (msg);
    }
}