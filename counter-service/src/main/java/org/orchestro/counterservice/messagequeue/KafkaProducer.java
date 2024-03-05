package org.orchestro.counterservice.messagequeue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orchestro.counterservice.dto.RequestedReceiptDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, RequestedReceiptDto> kafkaTemplate;

    public void send(String topic, RequestedReceiptDto payload) {
        log.info("sending payload={} to topic={}", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
