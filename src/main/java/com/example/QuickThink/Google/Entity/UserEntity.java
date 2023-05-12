package com.example.QuickThink.Google.Entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 유저 엔티티
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String googleName;

    @Column(nullable = false, length = 255)
    private String googleId;

    @Column(nullable = false, length = 255)
    private String profilePicture;

    @Column
    private String profileText = "";

    @Column(nullable = false, length = 255)
    private String accessToken;
}
