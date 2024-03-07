package org.orchestro.storeservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.orchestro.storeservice.dto.CoffeeDto;
import org.orchestro.storeservice.dto.ResponseCoffeeDto;
import org.orchestro.storeservice.jpa.CoffeeEntity;
import org.orchestro.storeservice.service.StoreService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final Environment env;

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

    // 커피 주문 상태 업데이트 -> TODO: Kafka 사용
    // @PostMapping("/coffee-order-update")


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time =" + env.getProperty("token.expiration_time"));
    }

}
