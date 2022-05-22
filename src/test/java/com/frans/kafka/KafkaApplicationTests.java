package com.frans.kafka;

import com.frans.kafka.producer.NotificationProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaApplicationTests {

	@Autowired
	NotificationProducer producer;

	@Test
	void contextLoads() {

		for(int i=200;i<300;i++){
			//notificationProducer.sendNotification(String.valueOf(i));
			producer.sendNotificationWithAck(String.valueOf(i+50));

		}

	}

}
