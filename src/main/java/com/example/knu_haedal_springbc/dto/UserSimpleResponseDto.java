package com.example.knu_haedal_springbc.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // Noarument~랑 비슷한 개념인데 모든 파라미터를 다 받는 컨스트럭터
public class UserSimpleResponseDto {
    private Long id;
    private String username;
    private String name;
    private String imageData;
    private Boolean isFollowing;
}
