package com.scholarshipmatcher.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="student_profiles") public class StudentProfile {
 @Id public Long userId;
 @MapsId @OneToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") public UserAccount user;
 @Size(max=120) public String studentName;
 @Size(max=100) public String state;
 @Size(max=50) public String category;
 @Size(max=30) public String gender;
 @Size(max=50) public String educationLevel;
 @Size(max=160) public String course;
 @Size(max=20) public String academicYear;
 @DecimalMin("0.0") @DecimalMax("100.0") public Double marks;
 @PositiveOrZero public Long familyIncome;
 public StudentProfile(){}
}
