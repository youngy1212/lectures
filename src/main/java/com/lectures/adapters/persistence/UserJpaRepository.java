package com.lectures.adapters.persistence;

import com.lectures.domain.user.User;
import com.lectures.domain.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {
}
