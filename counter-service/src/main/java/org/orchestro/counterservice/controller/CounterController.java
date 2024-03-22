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

    private final Environment env;

    // 사용자 커피 주문
    @PostMapping("/orders")
    public String coffeeOrder(@RequestBody RequestOrderDto requestOrderDto) {
        String orderId = counterService.orderCoffee(requestOrderDto);

        return String.format("[%s 주문] %s 커피를 %s개 주문하였습니다.", orderId, requestOrderDto.getCoffeeName(), requestOrderDto.getQty());
    }

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
    @GetMapping("/orders-success")
    public ResponseEntity<List<CoffeeOrderStatusDto>> getOrderSuccess() {
        List<CoffeeOrderStatusDto> successOrder = counterService.getOrderStatus("SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(successOrder);
    }


    // 대기중인 주문들 조회
    @GetMapping("/orders-pending")
    public ResponseEntity<List<CoffeeOrderStatusDto>> getOrderPending() {
        List<CoffeeOrderStatusDto> successOrder = counterService.getOrderStatus("PENDING");
        return ResponseEntity.status(HttpStatus.OK).body(successOrder);
    }

    // 실패한 주문들 조회
    @GetMapping("/orders-failed")
    public ResponseEntity<List<CoffeeOrderStatusDto>> getOrderFailed() {
        List<CoffeeOrderStatusDto> successOrder = counterService.getOrderStatus("FAIL");
        return ResponseEntity.status(HttpStatus.OK).body(successOrder);
    }


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time =" + env.getProperty("token.expiration_time"));
    }
}
