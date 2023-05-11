package com.example.QuickThink.Google;

import com.example.QuickThink.Google.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<UserEntity, Long> {
}
