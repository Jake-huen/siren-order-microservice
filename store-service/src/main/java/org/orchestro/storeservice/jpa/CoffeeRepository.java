package org.orchestro.storeservice.jpa;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<CoffeeEntity, Long> {
    Optional<CoffeeEntity> findByCoffeeName(String coffeeName);

    Optional<CoffeeEntity> findByCoffeeId(String coffeeId);

}
