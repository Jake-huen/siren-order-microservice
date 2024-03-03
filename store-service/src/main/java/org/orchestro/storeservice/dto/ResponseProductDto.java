package org.orchestro.storeservice.dto;


import lombok.Data;

@Data
public class ResponseProductDto {

    private String productId;
    private String qty;
    private Integer unitPrice;
}
