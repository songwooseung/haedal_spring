package com.example.knu_haedal_springbc.service;

import com.example.knu_haedal_springbc.dto.UserSimpleResponseDto;
import com.example.knu_haedal_springbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.knu_haedal_springbc.domain.User;
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserSimpleResponseDto saveUser(User newUser) {
        // 중복 회원 검증
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new IllegalStateException("중복되는 username 입니다.");
        }

        userRepository.save(newUser);
        return convertUserToSimpleDto(newUser, newUser); // user에서 데이터를 뽑고 싱글 response dto로 변환해줌
    }

    public UserSimpleResponseDto convertUserToSimpleDto(User currentUser, User targetUser) {
        return new UserSimpleResponseDto(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getName(),
                null,
                false
        );
    }
}
