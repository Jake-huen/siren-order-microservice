package org.orchestro.apigateway;


import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/health_check")
    @Timed(value = "users.status", longTask = true)
    public ResponseEntity<String> status() {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("It's Working in User Service"
                + ", \nport(local.server.port)=" + env.getProperty("local.server.port")
                + ", \nport(server.port)=" + env.getProperty("server.port")
                + ", \ntoken secret=" + env.getProperty("token.secret")
                + ", \ntoken expiration time =" + env.getProperty("token.expiration_time")
                + ", \nGreeting message =" + env.getProperty("greeting")));
    }
}
