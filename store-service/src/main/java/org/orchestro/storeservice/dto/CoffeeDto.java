package org.orchestro.storeservice.dto;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CoffeeDto {
    private String coffeeId;
    private String coffeeName;
    private Integer stock;
    private Integer unitPrice;
    private Integer coffeeBrewTime;
}
