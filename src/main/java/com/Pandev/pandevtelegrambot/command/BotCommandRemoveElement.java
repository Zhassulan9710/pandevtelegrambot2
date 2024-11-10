package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.service.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandRemoveElement implements Command {
    private final CategoryService categoryService;

    public BotCommandRemoveElement(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public String execute(Update update) {
        String[] commandParts = update.getMessage().getText().split(" ");
        if (commandParts.length != 2) {
            return "Неверный формат команды. Используйте: /removeElement <название элемента>";
        }
        String categoryName = commandParts[1];
        boolean removed = categoryService.removeCategory(categoryName);
        return removed ? "Элемент \"" + categoryName + "\" удален." : "Элемент \"" + categoryName + "\" не найден.";
    }
}
