package org.orchestro.storeservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.orchestro.storeservice.dto.CoffeeDto;
import org.orchestro.storeservice.dto.CoffeeEditDto;
import org.orchestro.storeservice.dto.ResponseCoffeeDto;
import org.orchestro.storeservice.jpa.CoffeeEntity;
import org.orchestro.storeservice.service.StoreService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final Environment env;

    // 커피 모든 메뉴 조회
    @GetMapping("/coffee")
    public ResponseEntity<List<ResponseCoffeeDto>> getAllCoffeeMenus() {
        Iterable<CoffeeEntity> allCoffees = storeService.getAllCoffees();
        List<ResponseCoffeeDto> result = new ArrayList<>();
        allCoffees.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCoffeeDto.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 커피 단일 메뉴 조회
    @GetMapping("/coffee/{coffeeName}")
    public ResponseEntity<ResponseCoffeeDto> getCoffeeMenuByName(@PathVariable("coffeeName") String coffeeName) {
        log.info("Before retrieve CoffeeName data");
        CoffeeEntity coffeeEntity = storeService.getCoffee(coffeeName);
        ResponseCoffeeDto result = new ModelMapper().map(coffeeEntity, ResponseCoffeeDto.class);
        log.info("After retrieve Coffee data");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 커피 메뉴 등록
    @PostMapping("/coffee")
    public ResponseEntity<ResponseCoffeeDto> createCoffee(@RequestBody CoffeeDto coffeeDto) {
        CoffeeEntity coffeeEntity = storeService.createCoffee(coffeeDto);
        ResponseCoffeeDto result = new ModelMapper().map(coffeeEntity, ResponseCoffeeDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 커피 메뉴 수정
    @PutMapping("/coffee/{coffeeId}")
    public ResponseEntity<ResponseCoffeeDto> updateCoffeeMenu(@PathVariable("coffeeId") String coffeeId, @RequestBody CoffeeEditDto coffeeEditDto) {
        CoffeeEntity coffeeEntity = storeService.updateCoffee(coffeeId, coffeeEditDto);
        ResponseCoffeeDto result = new ModelMapper().map(coffeeEntity, ResponseCoffeeDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 커피 메뉴 삭제
    @DeleteMapping("/coffee/{coffeeId}")
    public ResponseEntity<String> deleteCoffeeMenu(@PathVariable("coffeeId") String coffeeId) {
        storeService.deleteCoffee(coffeeId);
        return ResponseEntity.status(HttpStatus.OK).body(coffeeId + "가 성공적으로 삭제되었습니다.");
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", \nport(local.server.port)=" + env.getProperty("local.server.port")
                + ", \nport(server.port)=" + env.getProperty("server.port")
                + ", \ntoken secret=" + env.getProperty("token.secret")
                + ", \ntoken expiration time =" + env.getProperty("token.expiration_time")
                + ", \nGreeting message =" + env.getProperty("greeting"));
    }

}
