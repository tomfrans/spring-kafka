package com.frans.kafka.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;
import java.util.Locale;

@Component
public class NotificationProducer {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationProducer.class);

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Value(value="${kafka.notification.topic}")
    private String notificationTopic;

    // Normal kafka producer without waiting for the acknowledgement
    public void sendNotification(String key){
        LOG.info("Sending Notification started :"+new Date());
        Message<String> message = MessageBuilder
                .withPayload("Hello Mr. T , your application is approved for:"+key)
                .setHeader(KafkaHeaders.TOPIC, notificationTopic)
                .setHeader(KafkaHeaders.KEY, key)
                //.setHeader(KafkaHeaders.PARTITION, 0)
                .setHeader("X-Custom-Header", "Sending Custom Header with Spring Kafka")
                        .build();

        kafkaTemplate.send(message);
        LOG.info("Sending Notification completed :"+new Date());
    }

    public void sendNotificationWithAck(String key){
        LOG.info("Sending Notification with ACK started :"+new Date());
        String msg = "Hello Mr. T , your application is approved for:"+key;
        Message<String> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, notificationTopic)
                .setHeader(KafkaHeaders.KEY, key)
                //.setHeader(KafkaHeaders.PARTITION, 0)
                .setHeader("X-Custom-Header", "Custom Header with Spring Kafka")
                .build();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOG.info("Sent message=[" + message +"] with offset=[" + result.getRecordMetadata().offset() + "]"+new Date());

            }
            @Override
            public void onFailure(Throwable ex) {
                LOG.info("Unable to send message=["+ message + "] due to : " + ex.getMessage());
            }
        });
        LOG.info("Sending Notification with ACK completed :"+new Date());

    }
}
