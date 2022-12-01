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
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

@RestController
@RequestMapping("/v1")
public class OrderController {
	
	@Autowired
	Mqtt5BlockingClient client;
	
	
	@GetMapping(path = "/hello")
	public String hello() {
		return "Hello World";
	}
	
}
