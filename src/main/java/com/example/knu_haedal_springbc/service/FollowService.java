package com.example.knu_haedal_springbc.service;

import com.example.knu_haedal_springbc.domain.Follow;
import com.example.knu_haedal_springbc.domain.User;
import com.example.knu_haedal_springbc.dto.UserSimpleResponseDto;
import com.example.knu_haedal_springbc.repository.FollowRepository;
import com.example.knu_haedal_springbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public FollowService(FollowRepository followRepository, UserRepository userRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void followUser(User currentUser, Long targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팔로잉 회원입니다."));

        if (currentUser.getId().equals(targetUserId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우 할 수 없습니다.");
        }
        if (followRepository.existsByFollowerAndFollowing(currentUser, targetUser)) {
            throw new IllegalStateException("이미 팔로우중입니다.");
        }

        Follow follow = new Follow(currentUser, targetUser);
        followRepository.save(follow);
    }

    public void unfollowUser(User currentUser, Long targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Follow follow = followRepository.findByFollowerAndFollowing(currentUser, targetUser)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 하고있지 않습니다."));

        followRepository.delete(follow);
    }


    public List<UserSimpleResponseDto> getFollowingUsers(User currentUser, Long targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        List<Follow> follows = followRepository.findByFollower(targetUser);
        return follows.stream()
                .map(follow -> userService.convertUserToSimpleDto(currentUser, follow.getFollowing()))
                .toList();
    }


    public List<UserSimpleResponseDto> getFollowerUsers(User currentUser, Long targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        List<Follow> follows = followRepository.findByFollowing(targetUser);
        return follows.stream()
                .map(follow -> userService.convertUserToSimpleDto(currentUser, follow.getFollower()))
                .toList();
    }
}
