package com.javasampleapproach.rabbitmq.subcriber;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.javasampleapproach.rabbitmq.sender.Sender;
import com.rabbitmq.client.Channel;



@Component
public class Subcriber {

	@Autowired
    Sender sender;
	
	Channel channel;
	public long tag;
	
	@RabbitListener(queues="${jgg.rabbitmq.queue}", containerFactory="rabbitListenerContainerFactory")
    //public String recievedMessage(String msg) {
		public void recievedMessage(byte[] bytes, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		String msg = new String(bytes);
        System.out.println(new Timestamp(System.currentTimeMillis()) + " - Recieved Message: " + msg);
        ListenableFuture<SendResult<String, String>> future = sender.send(msg);
        this.channel = channel;
        this.tag = tag;
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
            	System.out.println("Sent sample message");           	
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message due to : " + ex.getMessage());
            }
        });
        try {
			System.out.println(future.get().getProducerRecord().value());
			channel.basicAck(tag, false);
			
		} catch (ExecutionException | InterruptedException e) {
			channel.basicNack(tag, false, true);
			e.printStackTrace();
		}
    }
}