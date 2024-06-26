package com.example.checkcheck.repository;

import com.example.checkcheck.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);

}

