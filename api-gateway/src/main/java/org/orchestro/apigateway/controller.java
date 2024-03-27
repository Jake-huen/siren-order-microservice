package org.orchestro.apigateway;


import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class controller {

    private final Environment env;

    @GetMapping("/check")
    public String get() {
        return env.getProperty("check");
    }
}
