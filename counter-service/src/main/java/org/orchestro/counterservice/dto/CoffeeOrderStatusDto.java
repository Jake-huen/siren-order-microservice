package org.orchestro.counterservice.dto;

import lombok.Data;

@Data
public class CoffeeOrderStatusDto {
    private String coffeeId;
    private String coffeeName;
    private String orderId;
    private String userId;
    private String status;
}
