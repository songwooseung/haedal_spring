package com.example.knu_haedal_springbc.controller;

import com.example.knu_haedal_springbc.domain.User;
import com.example.knu_haedal_springbc.dto.UserDetailResponseDto;
import com.example.knu_haedal_springbc.dto.UserSimpleResponseDto;
import com.example.knu_haedal_springbc.dto.UserUpdateRequestDto;
import com.example.knu_haedal_springbc.service.AuthService;
import com.example.knu_haedal_springbc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/users") // 유저 정보 간략 조회
    public ResponseEntity<List<UserSimpleResponseDto>> getUsers(@RequestParam(required = false)String username, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        List<UserSimpleResponseDto> users;
        if (username == null || username.isEmpty()) {
            users = userService.getAllUsers(currentUser);
        } else {
            users = userService.getUserByUsername(currentUser, username);
        }

        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/profile") // 유저 정보 수정 (이미지 제외)
    public ResponseEntity<UserDetailResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);
        UserDetailResponseDto updated = userService.updateUser(currentUser, userUpdateRequestDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/users/{userId}/profile") // 유저 정보 상세 조회
    public ResponseEntity<UserDetailResponseDto>
    getUserProfile(@PathVariable Long userId, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        UserDetailResponseDto userDetailResponseDto = userService.getUserDetail(currentUser, userId);

        return ResponseEntity.ok(userDetailResponseDto);
    }


}
