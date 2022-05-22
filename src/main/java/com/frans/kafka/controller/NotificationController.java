package com.frans.kafka.controller;

import com.frans.kafka.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class NotificationController {

    @Autowired
    NotificationProducer producer;

    @GetMapping(value = "/push/notification")
    public void produceNotification(@RequestParam int limit){
        for(int i=limit;i<limit+1000;i++){
            producer.sendNotificationWithAck(String.valueOf(i));
        }
    }
}
