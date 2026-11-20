package com.scholarshipmatcher.entity;
import jakarta.persistence.*;
@Entity @Table(name="scholarships") public class Scholarship {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false,length=255) public String name;
 @Column(nullable=false,length=255) public String provider;
 @Column(nullable=false,length=500) public String sourceUrl;
 @Column(nullable=false,length=1000) public String description;
 @Column(nullable=false,length=100) public String verificationStatus;
 public String state,category,gender,educationLevel,course,academicYear;
 public Double minMarks; public Long maxIncome;
 @Column(length=255) public String benefit;
 @Column(length=40) public String openingDate;
 @Column(length=40) public String closingDate;
 @Column(length=40) public String lastVerified;
 public Boolean demonstration;
 protected Scholarship(){}
}
