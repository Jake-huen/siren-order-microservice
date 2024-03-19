package org.orchestro.storeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReceiptFromStoreDto {
    private String orderId;
    private String orderStatus;
    private String orderMessage;
}
