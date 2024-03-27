package org.orchestro.userservice.config;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitorConfig {
    @Bean
    public Capability capability(final MeterRegistry registry) {
         return new MicrometerCapability(registry);
    }
}
