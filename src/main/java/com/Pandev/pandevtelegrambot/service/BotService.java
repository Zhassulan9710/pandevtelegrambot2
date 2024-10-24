package com.Pandev.pandevtelegrambot.service;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.repository.BotRepository;

import java.util.List;

public class BotService {
    private final BotRepository botRepository;

    public BotService(BotRepository botRepository) {
        this.botRepository = botRepository;
    }
    public List getAllCategories() {
        return botRepository.findAll();
    }

    public void addCategory(String name, String parentName) {
        BotCategory botCategory = new BotCategory();
        botCategory.setName(name);
        if (parentName != null) {
            BotCategory parent = botRepository.findByName(parentName);
            botCategory.setParent(parent);
            parent.getChildren().add(botCategory);
        }
        botRepository.save(botCategory);
    }

    public void removeCategory(String name) {
        BotCategory category = botRepository.findByName(name);
        if (category != null) {
            botRepository.delete(category);
        } else {
            throw new IllegalArgumentException("Категория не найдена: " + name);
        }
    }
}
