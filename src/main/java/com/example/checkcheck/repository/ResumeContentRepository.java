package com.example.checkcheck.repository;

import com.example.checkcheck.entity.Resume;
import com.example.checkcheck.entity.ResumeContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeContentRepository extends JpaRepository<ResumeContent, Long> {
    List<ResumeContent> findResumeContentsByResume(Resume resume);
    List<ResumeContent> findResumeContentsByResumeId(Long resumeId);


}
