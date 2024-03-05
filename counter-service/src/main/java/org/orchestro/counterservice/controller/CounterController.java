package org.orchestro.counterservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.orchestro.counterservice.client.StoreServiceClient;
import org.orchestro.counterservice.dto.*;
import org.orchestro.counterservice.jpa.OrderEntity;
import org.orchestro.counterservice.kafka.KafkaProducer;
import org.orchestro.counterservice.service.CounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;
    private final StoreServiceClient storeServiceClient;

    // private final KafkaProducer kafkaProducer;
    private final String topic = "coffee-store-ordered-events";

    // 사용자 커피 목록 조회
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        log.info("Before retrieve orders data");
        Iterable<OrderEntity> orderList = counterService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        log.info("After retrieved orders data");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 커피 이름으로 커피 주문
    @PostMapping("/coffee-order")
    public String coffeeOrder(@RequestBody RequestOrderDto requestOrderDto) {
        CoffeeDto coffeeByCoffeeName = storeServiceClient.getCoffeeByCoffeeName(requestOrderDto.getCoffeeName());
        RequestedReceiptDto payload = RequestedReceiptDto.builder()
                .coffeeId(coffeeByCoffeeName.getCoffeeId())
                .coffeeName(coffeeByCoffeeName.getCoffeeName())
                .qty(coffeeByCoffeeName.getStock())
                .orderId(UUID.randomUUID().toString())
                .createdAt(new Date())
                .build();
        // 주문 내용 kafka 전달
        // kafkaProducer.send(topic, payload);
        log.info("topic: {}", topic);
        log.info("payload: {}", payload);
        return "주문을 완료하였습니다.";
    }
}
