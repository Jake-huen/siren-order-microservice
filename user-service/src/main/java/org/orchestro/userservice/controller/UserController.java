package org.orchestro.userservice.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.orchestro.userservice.dto.UserDto;
import org.orchestro.userservice.jpa.UserEntity;
import org.orchestro.userservice.service.UserService;
import org.orchestro.userservice.vo.RequestUser;
import org.orchestro.userservice.vo.ResponseUser;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Environment env;

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

    @GetMapping("/config-check")
    public String configCheck() {
        return env.getProperty("greeting");
    }

    // 전체 사용자 조회
    @GetMapping("/users")
    @Timed(value = "users.getAllUsers", longTask = true)
    public ResponseEntity<List<ResponseUser>> getAllUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(user -> {
            result.add(new ModelMapper().map(user, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 회원가입
    @PostMapping("/users")
    @Timed(value = "users.createUser", longTask = true)
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);

        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    // 사용자 정보, 커피 주문 내역 조회
    @GetMapping("/users/{userId}")
    @Timed(value = "users.getCounterContentsAndUser", longTask = true)
    public ResponseEntity<ResponseUser> getUserByUserId(@PathVariable String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser result = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
}
