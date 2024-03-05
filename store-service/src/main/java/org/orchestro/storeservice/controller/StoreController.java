package org.orchestro.storeservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.orchestro.storeservice.dto.CoffeeDto;
import org.orchestro.storeservice.dto.ResponseCoffeeDto;
import org.orchestro.storeservice.jpa.CoffeeEntity;
import org.orchestro.storeservice.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/coffee")
    public ResponseEntity<List<ResponseCoffeeDto>> getAllCoffeeMenus() {
        Iterable<CoffeeEntity> allCoffees = storeService.getAllCoffees();
        List<ResponseCoffeeDto> result = new ArrayList<>();
        allCoffees.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCoffeeDto.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/coffee/{coffeeName}")
    public ResponseEntity<ResponseCoffeeDto> getCoffeeMenu(@PathVariable("coffeeName") String coffeeName) {
        CoffeeEntity coffeeEntity = storeService.getCoffee(coffeeName);
        ResponseCoffeeDto result = new ModelMapper().map(coffeeEntity, ResponseCoffeeDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/coffee")
    public ResponseEntity<ResponseCoffeeDto> createCoffee(@RequestBody CoffeeDto coffeeDto) {
        CoffeeEntity coffeeEntity = storeService.createCoffee(coffeeDto);
        ResponseCoffeeDto result = new ModelMapper().map(coffeeEntity, ResponseCoffeeDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
