package com.mqtt;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

public class MyMqttMain {
	
	public static void main(String[] args) throws Exception {

        final String host = "cbaa45c2368e4ffcadc4f53fc20a3a3d.s2.eu.hivemq.cloud";
        final String username = "root@123";
        final String password = "root@123";

        // create an MQTT client
        final Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .buildBlocking();

        // connect to HiveMQ Cloud with TLS and username/pw
        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();

        System.out.println("Connected successfully");

        // subscribe to the topic "my/test/topic"
        client.subscribeWith()
                .topicFilter("my/test/topic")
                .send();

        // set a callback that is called when a message is received (using the async API style)
        client.toAsync().publishes(ALL, publish -> {
            System.out.println("Received message: " +
                publish.getTopic() + " -> " +
                UTF_8.decode(publish.getPayload().get()));

            // disconnect the client after a message was received
           // client.disconnect();
        });

        // publish a message to the topic "my/test/topic"
//       System.err.println( client.publishWith()
//                .topic("my/test/topic")
//                .payload(UTF_8.encode("Hello2"))
//                .send().toString());
    }
	

}
