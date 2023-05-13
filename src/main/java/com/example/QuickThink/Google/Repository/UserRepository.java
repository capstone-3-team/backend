package com.example.QuickThink.Google.Repository;

import com.example.QuickThink.Google.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByGoogleId(String googleId);
    UserEntity findByGoogleIdAndAccessToken(String googleId, String accessToken);
    UserEntity findByAccessToken(String accessToken);
    List<UserEntity> findAllByGoogleNameContaining(String googleName);
}
