package org.orchestro.counterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String orderId;
    private String coffeeName;
    private Integer qty;
}
