package org.orchestro.counterservice.client;

import org.orchestro.counterservice.dto.CoffeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "store-service")
public interface StoreServiceClient {

    // store-service의 내용 호출
    @GetMapping("/coffee/{coffeeName}")
    CoffeeDto getCoffeeByCoffeeName(@PathVariable String coffeeName);

}

