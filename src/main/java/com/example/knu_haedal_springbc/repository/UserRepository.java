package com.example.knu_haedal_springbc.repository;

import com.example.knu_haedal_springbc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { //jpa 디비와 코드를 매핑해줌.
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
