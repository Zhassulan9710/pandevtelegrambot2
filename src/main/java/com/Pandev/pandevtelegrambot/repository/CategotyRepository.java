package com.Pandev.pandevtelegrambot.repository;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//изменил BotRepository на CategoryRepository
@Repository
public interface CategotyRepository extends JpaRepository<BotCategory, Long> {
    Optional<BotCategory> findByName(String name);
}

