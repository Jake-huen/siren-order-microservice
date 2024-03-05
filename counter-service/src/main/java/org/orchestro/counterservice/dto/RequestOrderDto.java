package org.orchestro.counterservice.dto;

import lombok.Data;

@Data
public class RequestOrderDto {
    private String userId;
    private String coffeeName;
    private Integer qty;
}
