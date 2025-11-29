package com.rohit.careerNetworkingPlatform.userService.service;

import com.rohit.careerNetworkingPlatform.userService.dto.LoginRequestDto;
import com.rohit.careerNetworkingPlatform.userService.dto.SignUpRequestDto;
import com.rohit.careerNetworkingPlatform.userService.dto.UserDto;
import com.rohit.careerNetworkingPlatform.userService.entity.User;
import com.rohit.careerNetworkingPlatform.userService.event.UserCreatedEvent;
import com.rohit.careerNetworkingPlatform.userService.exception.BadRequestException;
import com.rohit.careerNetworkingPlatform.userService.exception.ResourceNotFoundException;
import com.rohit.careerNetworkingPlatform.userService.repository.UserRepository;
import com.rohit.careerNetworkingPlatform.userService.util.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final KafkaTemplate<Long, UserCreatedEvent> userCreatedEventKafkaTemplate;

    public UserDto signup(SignUpRequestDto signUpRequestDto) {
        log.info("Signup a user with email : {}", signUpRequestDto.getEmail());

        boolean exists = userRepository.existsByEmail(signUpRequestDto.getEmail());
        if (exists) {
            throw new BadRequestException("User already exists");
        }

        User user = modelMapper.map(signUpRequestDto, User.class);
        user.setPassword(BCrypt.hash(signUpRequestDto.getPassword()));

        user = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();

        userCreatedEventKafkaTemplate.send("user_created_topic", userCreatedEvent);

        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login request for user with email : {}", loginRequestDto.getEmail());

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadRequestException("Incorrect email or password"));

        boolean isPasswordMatch = BCrypt.match(loginRequestDto.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            throw new BadRequestException("Incorrect email or password");
        }

        return jwtService.generateAccessToken(user);
    }
}
