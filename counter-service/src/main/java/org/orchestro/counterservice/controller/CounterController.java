package org.orchestro.counterservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.orchestro.counterservice.client.StoreServiceClient;
import org.orchestro.counterservice.dto.*;
import org.orchestro.counterservice.jpa.OrderEntity;
import org.orchestro.counterservice.jpa.OrderRepository;
import org.orchestro.counterservice.messagequeue.KafkaProducer;
import org.orchestro.counterservice.service.CounterService;
import org.springframework.core.env.Environment;
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
    private final Environment env;

    private final KafkaProducer kafkaProducer;
    private final OrderRepository orderRepository;

    // 사용자 커피 주문
    @PostMapping("/orders")
    public String coffeeOrder(@RequestBody RequestOrderDto requestOrderDto) {
        CoffeeDto coffeeByCoffeeName = storeServiceClient.getCoffeeByCoffeeName(requestOrderDto.getCoffeeName());

        RequestedReceiptDto payload = RequestedReceiptDto.builder()
                .coffeeId(coffeeByCoffeeName.getCoffeeId())
                .coffeeName(coffeeByCoffeeName.getCoffeeName())
                .qty(requestOrderDto.getQty())
                .orderId(UUID.randomUUID().toString())
                .userId(requestOrderDto.getUserId())
                .createdAt(new Date())
                .build();
        // 주문 내용 kafka 전달
        String topic = "coffee-store-ordered-events";
        kafkaProducer.send(topic, payload);
        // DB에 주문 내용 저장
        Integer totalPrice = coffeeByCoffeeName.getUnitPrice() * requestOrderDto.getQty();
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(coffeeByCoffeeName.getCoffeeId())
                .qty(requestOrderDto.getQty())
                .unitPrice(coffeeByCoffeeName.getUnitPrice())
                .totalPrice(totalPrice)
                .userId(requestOrderDto.getUserId())
                .orderId(UUID.randomUUID().toString())
                .orderStatus("PENDING") // 나중에 enum으로 수정
                .build();
        orderRepository.save(orderEntity);

        return "주문 정보를 저장하였습니다.";
    }

    // 제조 완료된 커피 DB 업데이트
    // TODO: Kafka Listener 이용

    // 사용자별 커피 주문 내역 조회
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

    // 완료된 주문들 조회
    // @GetMapping("/orders-success")


    // 대기중인 주문들 조회
    // @GetMapping("/orders-pending")

    // 실패한 주문들 조회
    // @GetMapping("/orders-failed")


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time =" + env.getProperty("token.expiration_time"));
    }
}
