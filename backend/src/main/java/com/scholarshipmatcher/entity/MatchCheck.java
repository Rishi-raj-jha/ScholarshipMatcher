package com.scholarshipmatcher.entity;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="matching_history") public class MatchCheck {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) @JoinColumn(name="user_id") public UserAccount user;
 @Column(nullable=false,length=120) public String studentName;
 @Column(nullable=false) public int potentialMatches;
 @Column(nullable=false) public int needsReview;
 @Column(nullable=false) public int notEligible;
 @Column(nullable=false) public Instant checkedAt=Instant.now();
 protected MatchCheck(){}
 public MatchCheck(UserAccount user,String name,int potential,int review,int not){this.user=user;studentName=name;potentialMatches=potential;needsReview=review;notEligible=not;}
}
