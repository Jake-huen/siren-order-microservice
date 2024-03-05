package org.orchestro.counterservice.kafka;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.orchestro.counterservice.config.DemoViewDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

//@Component
@Slf4j
@Data
public class KafkaConsumer {

    private CountDownLatch latch = new CountDownLatch(10);
    private List<DemoViewDTO> payloads = new ArrayList<>();
    private DemoViewDTO payload;

    // record 를 수신하기 위한 consumer 설정
    @KafkaListener(topics = "coffee-store-ordered-events",
            containerFactory = "filterListenerContainerFactory")
    public void receive(ConsumerRecord<String, DemoViewDTO> consumerRecord) {
        payload = consumerRecord.value();
        log.info("received payload = {}", payload.toString());
        payloads.add(payload);
        latch.countDown();
    }

    public List<DemoViewDTO> getPayloads() {
        return payloads;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}
