package com.example.QuickThink.Card.Repository;

import com.example.QuickThink.Card.Entity.CardEntity;
import com.example.QuickThink.Google.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    List<CardEntity> findAllByUser(UserEntity user);
}
