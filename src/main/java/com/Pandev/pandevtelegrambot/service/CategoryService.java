package com.Pandev.pandevtelegrambot.service;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.repository.CategotyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategotyRepository categotyRepository;

    public CategoryService(CategotyRepository categotyRepository) {
        this.categotyRepository = categotyRepository;
    }

    // Получение всех категорий из базы данных
    public List<BotCategory> getAllCategories() {
        return categotyRepository.findAll();
    }

    // Добавление новой категории в базу данных
    public void addCategory(BotCategory category) {
        categotyRepository.save(category);
    }

    // Удаление категории по имени
    public boolean removeCategory(String categoryName) {
        return categotyRepository.findByName(categoryName)
                .map(category -> {
                    categotyRepository.delete(category);
                    return true;
                })
                .orElse(false);
    }

    // Обновление списка категорий в базе данных
    public void updateCategories(List<BotCategory> categories) {
        for (BotCategory category : categories) {
            categotyRepository.save(category);
        }
    }

    // Строим текстовое представление всех категорий и их подкатегорий
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
}
