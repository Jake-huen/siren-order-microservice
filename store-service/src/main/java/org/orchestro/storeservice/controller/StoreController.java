package org.orchestro.storeservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.orchestro.storeservice.dto.ResponseProductDto;
import org.orchestro.storeservice.jpa.CoffeeEntity;
import org.orchestro.storeservice.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/product")
    public ResponseEntity<List<ResponseProductDto>> getProduct() {
        Iterable<CoffeeEntity> allCatalogs = storeService.getAllCoffees();
        List<ResponseProductDto> result = new ArrayList<>();
        allCatalogs.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseProductDto.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
