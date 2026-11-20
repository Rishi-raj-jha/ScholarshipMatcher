package com.scholarshipmatcher.entity;
import jakarta.persistence.*;
@Entity @Table(name="saved_scholarships",uniqueConstraints=@UniqueConstraint(columnNames={"user_id","scholarship_id"})) public class SavedScholarship {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) @JoinColumn(name="user_id") public UserAccount user;
 @ManyToOne(optional=false) @JoinColumn(name="scholarship_id") public Scholarship scholarship;
 protected SavedScholarship(){} public SavedScholarship(UserAccount u,Scholarship s){user=u;scholarship=s;}
}
