package com.scholarshipmatcher.repository;
import com.scholarshipmatcher.entity.ScholarshipApplication; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ApplicationRepository extends JpaRepository<ScholarshipApplication,Long>{List<ScholarshipApplication> findByUserId(Long id);}
