package org.orchestro.counterservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.orchestro.counterservice.client.StoreServiceClient;
import org.orchestro.counterservice.dto.*;
import org.orchestro.counterservice.jpa.OrderEntity;
import org.orchestro.counterservice.jpa.OrderRepository;
import org.orchestro.counterservice.messagequeue.KafkaProducer;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounterService {
    private final OrderRepository orderRepository;
    private final StoreServiceClient storeServiceClient;
    private final KafkaProducer kafkaProducer;
    private final CircuitBreakerFactory circuitBreakerFactory;

    private final static String orderCoffeeTopic = "coffee-store-ordered-events";

    public CoffeeOrderDto createOrder(CoffeeOrderDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderEntity orderEntity = modelMapper.map(orderDetails, OrderEntity.class);

        orderRepository.save(orderEntity);

        return modelMapper.map(orderEntity, CoffeeOrderDto.class);
    }

    public CoffeeOrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        return new ModelMapper().map(orderEntity, CoffeeOrderDto.class);
    }

    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public String orderCoffee(RequestOrderDto requestOrderDto) {
        // ZIPKIN
        log.info("Before call Store microservice");
        // CircuitBreaker
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        CoffeeDto coffeeByCoffeeName = circuitbreaker.run(() -> storeServiceClient.getCoffeeByCoffeeName(requestOrderDto.getCoffeeName()),
                throwable -> new CoffeeDto());
        // CoffeeDto coffeeByCoffeeName = storeServiceClient.getCoffeeByCoffeeName(requestOrderDto.getCoffeeName());
        log.info("After call Store microservice");
        RequestedReceiptDto payload = RequestedReceiptDto.builder()
                .coffeeId(coffeeByCoffeeName.getCoffeeId())
                .coffeeName(coffeeByCoffeeName.getCoffeeName())
                .qty(requestOrderDto.getQty())
                .orderId(UUID.randomUUID().toString())
                .userId(requestOrderDto.getUserId())
                .createdAt(new Date())
                .build();
        // 주문 내용 kafka 전달
        log.info("[카프카 전달 전]");
        kafkaProducer.send(orderCoffeeTopic, payload);
        log.info("[카프카 전달 완료]");
        // DB에 주문 내용 저장
        Integer totalPrice = coffeeByCoffeeName.getUnitPrice() * requestOrderDto.getQty();
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(payload.getCoffeeId())
                .qty(payload.getQty())
                .unitPrice(coffeeByCoffeeName.getUnitPrice())
                .totalPrice(totalPrice)
                .userId(payload.getUserId())
                .orderId(payload.getOrderId())
                .orderStatus("PENDING")
                .build();
        log.info("total Price = {}", totalPrice);
        orderRepository.save(orderEntity);
        return payload.getOrderId();
    }

    public List<CoffeeOrderStatusDto> getOrderStatus(String status) {
        // status에 해당하는 주문들 모두 조회
        List<OrderEntity> orderEntities = orderRepository.findByOrderStatus(status);

        List<CoffeeOrderStatusDto> result = new ArrayList<>();

        orderEntities.forEach(v -> {
            result.add(new ModelMapper().map(v, CoffeeOrderStatusDto.class));
        });
        return result;
    }
}
