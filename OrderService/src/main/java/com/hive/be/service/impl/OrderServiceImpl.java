package com.hive.be.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chip.be.ReadTaskBar;
import com.hive.be.service.OrderService;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class OrderServiceImpl implements OrderService {

	
	@Autowired
	Mqtt5BlockingClient client;
	
	@Autowired
	ReadTaskBar taskbarReader;
	
	@Override
	@Scheduled(fixedRate = 5000)
	public void openedApp() {
		List<String> list = taskbarReader.readOpnedApp();
		if(list!=null){
			list.forEach(str->{
			System.out.println(str);
			client.publishWith().topic("my/test/topic")
			.payload(UTF_8.encode(str))
			.send();
			});
		}
	}

}
