package com.hive.be.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class DoOnStartUp {

	@Autowired
	Mqtt5BlockingClient client;
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void consumeMessage() {
		client.subscribeWith()
        .topicFilter("my/test/topic")
        .send();
		
		client.toAsync().publishes(ALL, publish -> {
            System.out.println("Received message: " +
                publish.getTopic() + " -> " +
                UTF_8.decode(publish.getPayload().get()));
        });
		
	}
	

}
