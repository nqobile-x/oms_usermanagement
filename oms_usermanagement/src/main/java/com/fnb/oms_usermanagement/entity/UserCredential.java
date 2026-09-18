package com.fnb.oms_usermanagement.entity;
import jakarta.persistence.*;
import  lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_credentials")
@Builder
public class UserCredential {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//ONE-TO-ONE relationship with User entity
@OneToOne
//JOIN column to link UserCredential with User entity
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
@Column(nullable = false)
    private String password;

}
