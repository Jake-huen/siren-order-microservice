package org.orchestro.storeservice.jpa;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

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

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

    public CoffeeEntity() {
    }

    @Builder
    public CoffeeEntity(String coffeeId, String coffeeName, Integer stock, Integer unitPrice, Integer coffeeBrewTime) {
        this.coffeeId = coffeeId;
        this.coffeeName = coffeeName;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.coffeeBrewTime = coffeeBrewTime;
    }
}
