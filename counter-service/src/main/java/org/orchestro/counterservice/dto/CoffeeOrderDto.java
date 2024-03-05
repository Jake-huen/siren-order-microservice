package org.orchestro.counterservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoffeeOrderDto implements Serializable {

    private String coffeeId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
