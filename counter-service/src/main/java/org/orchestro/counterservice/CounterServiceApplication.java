package org.orchestro.counterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CounterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CounterServiceApplication.class, args);
    }

}
