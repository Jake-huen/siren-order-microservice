package org.orchestro.storeservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.orchestro.storeservice.dto.CoffeeDto;
import org.orchestro.storeservice.jpa.CoffeeEntity;
import org.orchestro.storeservice.jpa.CoffeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final CoffeeRepository coffeeRepository;

    public Iterable<CoffeeEntity> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    public CoffeeEntity getCoffee(String coffeeName) {
        return coffeeRepository.findByCoffeeName(coffeeName);
    }

    public CoffeeEntity createCoffee(CoffeeDto coffeeDto) {
        CoffeeEntity coffeeEntity = CoffeeEntity.builder()
                .coffeeId(coffeeDto.getCoffeeId())
                .coffeeName(coffeeDto.getCoffeeName())
                .stock(coffeeDto.getStock())
                .unitPrice(coffeeDto.getUnitPrice())
                .coffeeBrewTime(coffeeDto.getCoffeeBrewTime())
                .build();
        return coffeeRepository.save(coffeeEntity);
    }
}
