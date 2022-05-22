package com.frans.kafka.consumer;

import com.frans.kafka.producer.NotificationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationConsumer.class);


    @KafkaListener(topics = "${kafka.notification.topic}",groupId = "${kafka.groupId}")
    public void consumeNotificationMessages(@Payload String message,
                                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition){
     LOG.info("Consumed Message: "+message + "from partition: "+partition);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic ="${kafka.notification.topic}",
    partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0")}),groupId = "${kafka.groupId}")
    public void consumeNotificationMessagesWithPartition(@Payload String message,
                                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition){
        LOG.info("Consumed with Partition Message: "+message + "from partition: "+partition);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic ="${kafka.notification.topic}",
            partitions = {"1","2"}),groupId = "${kafka.groupId}")
    public void consumeNotificationMessagesWithoutPartitionOffset(@Payload String message,
                                                         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition){
        LOG.info("Consumed with Partition Message: "+message + "from partition: "+partition);
    }
}
