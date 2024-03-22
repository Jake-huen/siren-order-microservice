package org.orchestro.counterservice.dto;

import lombok.Data;

@Data
public class CoffeeOrderStatusDto {
    private String orderId;
    private String userId;
    private String status;
}
