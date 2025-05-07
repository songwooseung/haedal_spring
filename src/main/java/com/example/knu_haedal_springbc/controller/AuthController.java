package com.example.knu_haedal_springbc.controller;

import com.example.knu_haedal_springbc.domain.User;
import com.example.knu_haedal_springbc.dto.LoginRequestDto;
import com.example.knu_haedal_springbc.dto.UserRegistrationRequestDto;
import com.example.knu_haedal_springbc.dto.UserSimpleResponseDto;
import com.example.knu_haedal_springbc.service.AuthService;
import com.example.knu_haedal_springbc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(UserService userservice, AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserSimpleResponseDto> registerUser(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = new User(
                userRegistrationRequestDto.getUsername(),
                userRegistrationRequestDto.getPassword(),
                userRegistrationRequestDto.getName()
        );
        UserSimpleResponseDto savedUser = userService.saveUser(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/auth/login")
    ResponseEntity<UserSimpleResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        UserSimpleResponseDto userSimpleResponseDto = authService.login(loginRequestDto, request);

        return ResponseEntity.ok(userSimpleResponseDto);
    }

    @PostMapping("/auth/logout")
    ResponseEntity<Void> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/auth/me")
    public ResponseEntity<UserSimpleResponseDto> me(HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        UserSimpleResponseDto userSimpleResponseDto = userService.convertUserToSimpleDto(currentUser, currentUser);

        return ResponseEntity.ok(userSimpleResponseDto);

    }

}
