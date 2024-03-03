package org.orchestro.counterservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orchestro.counterservice.dto.RequestOrderDto;
import org.orchestro.counterservice.dto.RequestedReceiptDto;
import org.orchestro.counterservice.dto.ResponseCoffeeDto;
import org.orchestro.counterservice.kafka.KafkaProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CounterController {

    private final KafkaProducer kafkaProducer;
    private final String topic = "coffee-store-ordered-events";

    // 커피 목록 조회


    @PostMapping("/coffee-order")
    public String coffeeOrder(@RequestBody RequestOrderDto requestOrderDto) {
        // TODO: store-service에서 주문 커피 정보 가져오기
        RequestedReceiptDto payload = RequestedReceiptDto.builder()
//                .coffeeId()
//                .coffeeName()
//                .qty()
//                .orderId()
//                .createdAt(new Date())
                .build();

        kafkaProducer.send(topic, payload);
        return "주문을 완료하였습니다.";
    }
}
