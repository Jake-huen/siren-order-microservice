package org.orchestro.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "store-service")
public interface StoreServiceClient {

    // store-service의 내용 호출
//    @GetMapping("/store-service/coffee/coffeeName")
//    List<>
}
