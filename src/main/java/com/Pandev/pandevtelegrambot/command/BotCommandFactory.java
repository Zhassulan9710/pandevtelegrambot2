package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.handler.BotHandler;
import com.Pandev.pandevtelegrambot.service.CategoryService;
import com.Pandev.pandevtelegrambot.service.ExcelService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandFactory {
    private final CategoryService categoryService;
    private final ExcelService excelService;
    private final BotHandler botHandler;

    public BotCommandFactory(CategoryService categoryService, ExcelService excelService, BotHandler botHandler1) {
        this.categoryService = categoryService;
        this.excelService = excelService;
        this.botHandler = botHandler1;
    }

    public Command getCommand(Update update) {
        String commandText = update.getMessage().getText();
        switch (commandText) {
            case "/help":
                return new BotCommandHelp();
            case "/addElement":
                return new BotCommandAddElement(categoryService);
            case "/removeElement":
                return new BotCommandRemoveElement(categoryService);
            case "/viewTree":
                return new BotCommandViewTree(categoryService);
            case "/download":
                return new BotCommandDownload(categoryService, excelService, botHandler);
            case "/upload":
                return new BotCommandUpload(categoryService, excelService);
            default:
                return null;
        }
    }
}
