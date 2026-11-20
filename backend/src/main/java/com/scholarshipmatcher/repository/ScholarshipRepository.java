package com.scholarshipmatcher.repository;
import com.scholarshipmatcher.entity.Scholarship; import org.springframework.data.jpa.repository.JpaRepository;
public interface ScholarshipRepository extends JpaRepository<Scholarship,Long>{}
