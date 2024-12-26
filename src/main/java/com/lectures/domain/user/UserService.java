package com.lectures.domain.user;

import com.lectures.adapters.persistence.UserJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public User findUserById(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }
}
