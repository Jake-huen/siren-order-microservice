package org.orchestro.counterservice.messagequeue;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.orchestro.counterservice.dto.OrderReceiptFromStoreDto;
import org.orchestro.counterservice.jpa.OrderEntity;
import org.orchestro.counterservice.jpa.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final OrderRepository orderRepository;

    @Transactional
    @KafkaListener(topics = "coffee-store-ordered-update-events")
    public void receive(ConsumerRecord<String, OrderReceiptFromStoreDto> consumerRecord) {
        OrderReceiptFromStoreDto responseStoreAnswersDto = consumerRecord.value();

        log.info("[가게 커피 주문 완료 카운터에서 받음] {}", responseStoreAnswersDto.getOrderId());
        log.info("[가게 커피 주문 완료 카운터에서 받음] {}", responseStoreAnswersDto.getOrderStatus());
        log.info("[가게 커피 주문 완료 카운터에서 받음] {}", responseStoreAnswersDto.getOrderMessage());
        OrderEntity orderEntity = orderRepository.findByOrderId(responseStoreAnswersDto.getOrderId());
        if(responseStoreAnswersDto.getOrderStatus().equals("FAILED")){
            orderEntity.setOrderStatus("FAILED");
            orderRepository.save(orderEntity);
        } else if (responseStoreAnswersDto.getOrderStatus().equals("SUCCESS")) {
            orderEntity.setOrderStatus("SUCCESS");
            orderRepository.save(orderEntity);
        }
        log.info("DB 저장 완료");
    }


}
