package org.orchestro.storeservice.messagequeue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orchestro.storeservice.dto.OrderReceiptFromStoreDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, OrderReceiptFromStoreDto> kafkaTemplate;

    public void send(String topic, OrderReceiptFromStoreDto orderReceiptFromStoreDto) {
        kafkaTemplate.send(topic, orderReceiptFromStoreDto);
        log.info("[kafkaProducer] Store service 가 주문 완료 메시지 보냄 : " + orderReceiptFromStoreDto);
    }
}
