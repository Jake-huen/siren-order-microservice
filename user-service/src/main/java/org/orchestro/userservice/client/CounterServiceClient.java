package org.orchestro.userservice.client;

import org.orchestro.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "counter-service")
public interface CounterServiceClient {

    // counter-service의 내용 호출
    @GetMapping("/{userId}/orders")
    List<ResponseOrder> getUserOrders(@PathVariable("userId") String userId);

}
