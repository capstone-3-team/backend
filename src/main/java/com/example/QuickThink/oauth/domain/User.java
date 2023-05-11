package com.example.QuickThink.oauth.domain;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String userName;

    @Column(length = 200)
    private String userEmail;

    /*@Column(length = 200)
    private String password;*/

    /*@Column(length = 200)
    private String profile;*/
}

