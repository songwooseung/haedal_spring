package com.example.knu_haedal_springbc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor // user 생성자 대체
@Getter
@Setter

public class User {
    @Id // primary key로 자동 인식
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동으로 아이디 생성할 때 번호 매겨줌 순서대로
    private Long id;

    @Column(nullable = false, unique = true) // unique
    private String username; // 실제 로그인할 때 쓰는 id

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String bio; // 유저 정보

    @Column(name ="joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name ="image_at")
    private String imageUrl;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = null;
        this.joinedAt = LocalDateTime.now();
        this.imageUrl = null;
    }


}
