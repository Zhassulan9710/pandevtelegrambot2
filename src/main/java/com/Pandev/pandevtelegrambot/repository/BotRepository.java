package com.Pandev.pandevtelegrambot.repository;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotRepository extends JpaRepository {
    BotCategory findByName(String name);

}
