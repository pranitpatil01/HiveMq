package com.hive.be.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.lang.invoke.MethodHandles;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
@Configuration
public class MqttClientConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Value("${mqtt.config.clientId}")
	private String clientId;
	
	@Value("${mqtt.config.host}")
	private String host;
	
	@Value("${mqtt.config.port}")
	private String port;
	
	@Value("${mqtt.config.username}")
	private String username;
	
	@Value("${mqtt.config.password}")
	private String password;
	
	
	@Bean
	public Mqtt5BlockingClient client() {
		return MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(Integer.parseInt(port))
                .sslWithDefaultConfig()
                .buildBlocking();
	}
	
	@Bean
	public @NotNull Mqtt5ConnAck connection(Mqtt5BlockingClient client) {
		return  client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();
	}
	
	@EventListener(ContextClosedEvent.class)
    public void disconnectClient() {
		client().disconnect();
        System.out.println("client disconnected");
    }
	
	
}
