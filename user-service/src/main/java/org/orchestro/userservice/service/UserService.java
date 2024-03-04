package org.orchestro.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.orchestro.userservice.dto.UserDto;
import org.orchestro.userservice.jpa.UserEntity;
import org.orchestro.userservice.jpa.UserRepository;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));
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
        // TODO : counter-service에 요청해서 해당 사용자가 주문한 정보들 가져오기

        return userDto;
    }
}
