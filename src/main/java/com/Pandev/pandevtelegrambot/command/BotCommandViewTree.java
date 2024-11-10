package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.service.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandViewTree implements Command{

    private final CategoryService categoryService;

    public BotCommandViewTree(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public String execute(Update update) {
        return categoryService.viewCategoriesTree();
    }
}
