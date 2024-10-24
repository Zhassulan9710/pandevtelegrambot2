package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandViewTree implements Command{
    private final BotService botService;

    public BotCommandViewTree(BotService botService) {
        this.botService = botService;
    }

    @Override
    public String execute(Update update) {
        try {
            StringBuilder response = new StringBuilder("Дерево категорий:\n");
            for (BotCategory botCategory : botService.getAllCategories()) {
                response.append(categoryToString(botCategory, 0));
            }
            return response.toString();
        } catch (Exception e) {
            return "Ошибка при получении дерева категорий: " + e.getMessage();
        }
    }

    private String categoryToString(BotCategory botCategory, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append(botCategory.getName()).append("\n");
        for (BotCategory child : botCategory.getChildren()) {
            sb.append(categoryToString(child, level + 1));
        }
        return sb.toString();
    }
}
