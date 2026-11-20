package com.scholarshipmatcher.repository;
import com.scholarshipmatcher.entity.StudentProfile; import org.springframework.data.jpa.repository.JpaRepository;
public interface ProfileRepository extends JpaRepository<StudentProfile,Long>{}
