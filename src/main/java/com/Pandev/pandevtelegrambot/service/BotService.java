package com.Pandev.pandevtelegrambot.service;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.repository.BotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotService {
    private final BotRepository botRepository;

    public BotService(BotRepository botRepository) {
        this.botRepository = botRepository;
    }

    public List<BotCategory> getAllCategories() {
        return botRepository.findAll();
    }

    public void addCategory(BotCategory category) {
        botRepository.save(category);
    }

    public boolean removeCategory(String categoryName) {
        return botRepository.findByName(categoryName)
                .map(category -> {
                    botRepository.delete(category);
                    return true;
                })
                .orElse(false);
    }

    public String viewCategoriesTree() {
        StringBuilder sb = new StringBuilder();
        for (BotCategory category : getAllCategories()) {
            sb.append(category.getName()).append("\n");
            for (BotCategory child : category.getChildren()) {
                sb.append("  - ").append(child.getName()).append("\n");
            }
        }
        return sb.toString();
    }
    public void updateCategories(List<BotCategory> categories) {
        for (BotCategory category : categories) {
            botRepository.save(category);
        }
    }
}
