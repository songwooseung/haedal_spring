package com.example.knu_haedal_springbc.repository;

import com.example.knu_haedal_springbc.domain.Follow;
import com.example.knu_haedal_springbc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Long countByFollower(User user);
    Long countByFollowing(User user);
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowing(User following);
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    boolean existsByFollowerAndFollowing(User follower, User following);
}
