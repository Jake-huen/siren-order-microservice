package org.orchestro.storeservice.dto;


import lombok.Data;

@Data
public class ResponseCoffeeDto {
    private String coffeeId;
    private String coffeeName;
    private String stock;
    private Integer unitPrice;
    private Integer coffeeBrewTime;
    private String coffeeImage;
    private String coffeeDescription;
}
