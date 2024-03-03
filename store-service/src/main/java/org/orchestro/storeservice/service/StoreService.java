package org.orchestro.storeservice.service;

import lombok.RequiredArgsConstructor;
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
}
