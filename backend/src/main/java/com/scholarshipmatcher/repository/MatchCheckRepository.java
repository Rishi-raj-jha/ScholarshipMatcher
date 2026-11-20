package com.scholarshipmatcher.repository;
import com.scholarshipmatcher.entity.MatchCheck;import org.springframework.data.jpa.repository.JpaRepository;import java.util.List;
public interface MatchCheckRepository extends JpaRepository<MatchCheck,Long>{List<MatchCheck> findByUserIdOrderByCheckedAtDesc(Long userId);}
