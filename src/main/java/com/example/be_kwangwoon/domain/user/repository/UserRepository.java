package com.example.be_kwangwoon.domain.user.repository;

import com.example.be_kwangwoon.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
