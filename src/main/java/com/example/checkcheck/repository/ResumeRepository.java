package com.example.checkcheck.repository;

import com.example.checkcheck.entity.Resume;
import com.example.checkcheck.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findResumesByUserEntity(UserEntity userEntity);
}
