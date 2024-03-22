package org.orchestro.counterservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReceiptFromStoreDto {
    private String orderId;
    private String orderStatus;
    private String orderMessage;

    private String coffeeId;
    private String coffeeName;
    private Integer qty;
    private String userId;
    private Date createdAt;
}
