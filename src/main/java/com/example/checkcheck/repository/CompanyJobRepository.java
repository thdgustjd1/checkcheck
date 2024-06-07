package com.example.checkcheck.repository;

import com.example.checkcheck.entity.Company;
import com.example.checkcheck.entity.CompanyJob;
import com.example.checkcheck.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyJobRepository extends JpaRepository<CompanyJob, Long> {
    List<CompanyJob> findByCompany(Company company);
    Optional<CompanyJob> findCompanyJobByCompanyAndJob(Company company, Job job);
}
