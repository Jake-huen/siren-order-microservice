package org.orchestro.storeservice.messagequeue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.orchestro.storeservice.dto.RequestedReceiptDto;
import org.orchestro.storeservice.jpa.CoffeeEntity;
import org.orchestro.storeservice.jpa.CoffeeRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CoffeeRepository coffeeRepository;

    // Coffee 주문 정보 받음
    @KafkaListener(topics = "coffee-store-ordered-events")
    public void receive(ConsumerRecord<String, RequestedReceiptDto> consumerRecord) {
        RequestedReceiptDto requestedReceiptDto = consumerRecord.value();
        CoffeeEntity coffeeEntity = coffeeRepository.findByCoffeeName(requestedReceiptDto.getCoffeeName());
        log.info("coffeeEntity 수량 : {}", coffeeEntity.getStock());
        log.info("요청 수량 : {}", requestedReceiptDto.getQty());

        if (coffeeEntity.getStock() < requestedReceiptDto.getQty()) { // 요청 수량이 가진 수량보다 많을 때
            // TODO: 주문 실패 코드
            System.out.println("수량 부족으로 인한 주문 실패");
        } else if(coffeeEntity.getStock() >= requestedReceiptDto.getQty()) {
            // TODO : 주문 성공 코드
            System.out.println("주문 성공");
            coffeeEntity.setStock(coffeeEntity.getStock() - requestedReceiptDto.getQty());
            coffeeRepository.save(coffeeEntity);
        }
    }
}
