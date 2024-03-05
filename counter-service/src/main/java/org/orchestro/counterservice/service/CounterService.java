package org.orchestro.counterservice.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.orchestro.counterservice.dto.CoffeeOrderDto;
import org.orchestro.counterservice.jpa.OrderEntity;
import org.orchestro.counterservice.jpa.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CounterService {
    private final OrderRepository orderRepository;

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
}
