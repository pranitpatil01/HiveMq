package com.hive.be.controller;

import static java.nio.charset.StandardCharsets.UTF_8;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hive.be.pojo.MessagePayload;
import com.hive.be.service.OrderService;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

@RestController
@RequestMapping("/v1")
public class OrderController {
	
	@Autowired
	Mqtt5BlockingClient client;
	
	@Autowired
	OrderService service;
	
	@GetMapping(path = "/hello")
	public String hello() {
		return "Hello World";
	}

	@PostMapping(path = "/publish")
	public String publishMessage(@RequestBody MessagePayload payload) {
		return client.publishWith()
				.topic(payload.getTopic())
				.payload(UTF_8.encode(payload.getMessage()))
				.send().toString();
	}
	
	@PostMapping(path = "/publisheOpenedApp")
	public String publisheOpenedApp() {
			service.openedApp();
			return "Taskbar monitoring starts...!!!";
	}
}
