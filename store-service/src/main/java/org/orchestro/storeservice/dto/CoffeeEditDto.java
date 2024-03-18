package org.orchestro.storeservice.dto;


import lombok.Data;

@Data
public class CoffeeEditDto {
    private String coffeeName;
    private Integer unitPrice;
    private Integer coffeeBrewTime;
}
