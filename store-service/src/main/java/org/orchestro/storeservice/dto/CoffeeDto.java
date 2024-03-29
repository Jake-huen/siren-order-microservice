package org.orchestro.storeservice.dto;

import lombok.Data;

@Data
public class CoffeeDto {
    private String coffeeId;
    private String coffeeName;
    private Integer stock;
    private Integer unitPrice;
    private Integer coffeeBrewTime;
    private String coffeeImage;
    private String coffeeDescription;
}
