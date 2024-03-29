package org.orchestro.storeservice.jpa;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.orchestro.storeservice.dto.CoffeeEditDto;

import java.util.Date;
import java.util.Optional;

@Entity
@Data
@Table(name = "coffee")
public class CoffeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String coffeeId;
    @Column(nullable = false)
    private String coffeeName;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer unitPrice;
    @Column(nullable = false)
    private Integer coffeeBrewTime;

    private String coffeeImage;

    private String coffeeDescription;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

    public CoffeeEntity() {
    }

    @Builder
    public CoffeeEntity(String coffeeId, String coffeeName, Integer stock, Integer unitPrice, Integer coffeeBrewTime, String coffeeImage, String coffeeDescription) {
        this.coffeeId = coffeeId;
        this.coffeeName = coffeeName;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.coffeeBrewTime = coffeeBrewTime;
        this.coffeeImage = coffeeImage;
        this.coffeeDescription = coffeeDescription;
    }

    public void update(CoffeeEditDto coffeeEditDto) {
        Optional.ofNullable(coffeeEditDto.getCoffeeName()).ifPresent(name -> this.coffeeName = name);
        Optional.ofNullable(coffeeEditDto.getUnitPrice()).ifPresent(price -> this.unitPrice = price);
        Optional.ofNullable(coffeeEditDto.getCoffeeBrewTime()).ifPresent(brewTime -> this.coffeeBrewTime = brewTime);
        Optional.ofNullable(coffeeEditDto.getCoffeeImage()).ifPresent(coffeeImage -> this.coffeeImage = coffeeImage);
        Optional.ofNullable(coffeeEditDto.getCoffeeDescription()).ifPresent(coffeeDescription -> this.coffeeDescription = coffeeDescription);
    }
}
