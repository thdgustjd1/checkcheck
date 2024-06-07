package com.example.checkcheck.repository;

import com.example.checkcheck.entity.CompanyJob;
import com.example.checkcheck.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
    List<QuestionType> findQuestionTypesByCompanyJob(CompanyJob companyJob);
    Optional<QuestionType> findQuestionTypeByCompanyJobAndType(CompanyJob companyJob, String type);
}
