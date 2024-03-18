package org.orchestro.storeservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.orchestro.storeservice.dto.CoffeeDto;
import org.orchestro.storeservice.dto.CoffeeEditDto;
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
        return coffeeRepository.findByCoffeeName(coffeeName).orElseThrow(() -> new IllegalArgumentException("해당하는 커피 이름이 없습니다 : " + coffeeName));
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

    public CoffeeEntity getCoffeeByCoffeeId(String coffeeId) {
        return coffeeRepository.findByCoffeeId(coffeeId).orElseThrow(() -> new IllegalArgumentException("해당하는 커피 ID가 없습니다. ID : " + coffeeId));
    }


    @Transactional
    public CoffeeEntity updateCoffee(String coffeeId, CoffeeEditDto coffeeEditDto) {
        CoffeeEntity coffeeEntity = coffeeRepository.findByCoffeeId(coffeeId).orElseThrow(() -> new IllegalArgumentException("해당하는 커피 ID가 없습니다. ID : " + coffeeId));
        coffeeEntity.update(coffeeEditDto);
        return coffeeEntity;
    }

    @Transactional
    public void deleteCoffee(String coffeeId) {
        CoffeeEntity coffeeEntity = coffeeRepository.findByCoffeeId(coffeeId).orElseThrow(() -> new IllegalArgumentException("해당하는 커피 ID가 없습니다. ID : " + coffeeId));
        coffeeRepository.delete(coffeeEntity);
    }
}
