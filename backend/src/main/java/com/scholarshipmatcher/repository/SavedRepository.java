package com.scholarshipmatcher.repository;
import com.scholarshipmatcher.entity.SavedScholarship; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import java.util.Optional;
public interface SavedRepository extends JpaRepository<SavedScholarship,Long>{List<SavedScholarship> findByUserId(Long id); Optional<SavedScholarship> findByUserIdAndScholarshipId(Long u,Long s);}
