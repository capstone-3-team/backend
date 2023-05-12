package com.example.QuickThink.Card.Repository;

import com.example.QuickThink.Card.Entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
