package com.scholarshipmatcher.entity;
import jakarta.persistence.*;
@Entity @Table(name="users") public class UserAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false,unique=true,length=190) public String email;
 @Column(nullable=false) public String passwordHash;
 @Column(nullable=false,length=120) public String fullName;
 protected UserAccount(){} public UserAccount(String name,String email,String hash){this.fullName=name;this.email=email;this.passwordHash=hash;}
}
