package com.scholarshipmatcher.entity;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="scholarship_applications") public class ScholarshipApplication {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) @JoinColumn(name="user_id") public UserAccount user;
 @ManyToOne(optional=false) @JoinColumn(name="scholarship_id") public Scholarship scholarship;
 @Column(nullable=false) public String status;
 public Instant updatedAt=Instant.now();
 protected ScholarshipApplication(){} public ScholarshipApplication(UserAccount u,Scholarship s,String st){user=u;scholarship=s;status=st;}
}
