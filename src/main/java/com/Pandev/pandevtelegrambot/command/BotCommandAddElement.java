package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.service.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandAddElement implements Command {
    private final CategoryService categoryService;

    public BotCommandAddElement(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public String execute(Update update) {
        String[] commandParts = update.getMessage().getText().split(" ");
        if (commandParts.length != 2) {
            return "Неверный формат команды. Используйте: /addElement <название элемента>";
        }
        String categoryName = commandParts[1];
        BotCategory category = new BotCategory(categoryName);
        categoryService.addCategory(category);
        return "Элемент \"" + categoryName + "\" добавлен.";
    }
}
