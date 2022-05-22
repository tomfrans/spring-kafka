package com.frans.kafka;

import com.frans.kafka.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineStarter implements CommandLineRunner {

    @Autowired
    NotificationProducer notificationProducer;

    @Override
    public void run(String... args) throws Exception {

//        for(int i=0;i<5;i++){
//            //notificationProducer.sendNotification(String.valueOf(i));
//            notificationProducer.sendNotificationWithAck(String.valueOf(i+50));
//
//        }

    }
}
