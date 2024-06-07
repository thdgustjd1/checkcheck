package com.example.checkcheck.repository;

import com.example.checkcheck.entity.QuestionType;
import com.example.checkcheck.entity.Statics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaticsRepository extends JpaRepository<Statics, Long> {
    List<Statics>findStaticsByQuestionType(QuestionType questionType);
}
