package org.orchestro.counterservice.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.orchestro.counterservice.client.StoreServiceClient;
import org.orchestro.counterservice.dto.*;
import org.orchestro.counterservice.jpa.OrderEntity;
import org.orchestro.counterservice.jpa.OrderRepository;
import org.orchestro.counterservice.messagequeue.KafkaProducer;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CounterService {
    private final OrderRepository orderRepository;
    private final StoreServiceClient storeServiceClient;
    private final KafkaProducer kafkaProducer;

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

    public void orderCoffee(RequestOrderDto requestOrderDto) {
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
