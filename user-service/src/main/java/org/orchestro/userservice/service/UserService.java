package org.orchestro.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.orchestro.userservice.client.CounterServiceClient;
import org.orchestro.userservice.dto.UserDto;
import org.orchestro.userservice.jpa.UserEntity;
import org.orchestro.userservice.jpa.UserRepository;
import org.orchestro.userservice.vo.ResponseOrder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CounterServiceClient counterServiceClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));
        log.info("userEntity:{}", userEntity);
        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.class);
    }

    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        /* ZIPKIN */
        log.info("Before call Counter microservice");
        // List<ResponseOrder> userOrders = counterServiceClient.getUserOrders(userId);
        /* CircuitBreaker */
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        List<ResponseOrder> userOrders = circuitbreaker.run(() -> counterServiceClient.getUserOrders(userId),
                throwable -> new ArrayList<>());

        log.info("After call Counter microservice");
        userDto.setOrders(userOrders);

        return userDto;
    }

    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }
}
