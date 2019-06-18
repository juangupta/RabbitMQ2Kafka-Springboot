package com.javasampleapproach.rabbitmq.subcriber;

import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;


@Component
public class Subcriber {

	@RabbitListener(queues="${jgg.rabbitmq.queue}", containerFactory="rabbitListenerContainerFactory")
    //public String recievedMessage(String msg) {
		public void recievedMessage(byte[] bytes, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		String msg = new String(bytes);
        System.out.println(new Timestamp(System.currentTimeMillis()) + " - Recieved Message: " + msg);
        if (msg.equalsIgnoreCase("error")) {
			channel.basicNack(tag, false, true);
        }
        else {
			channel.basicAck(tag, false);
        }
        //return (new Timestamp(System.currentTimeMillis()) + " - Recieved Message: " + msg);
    }
}