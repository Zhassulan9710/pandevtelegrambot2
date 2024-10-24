package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.handler.BotHandler;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandFactory {
    private final BotService botService;
    private final BotHandler botHandler;

    public BotCommandFactory(BotService botService, BotHandler botHandler) {
        this.botService = botService;
        this.botHandler = botHandler;
    }

    public Command getCommand(Update update) {
        String commandText = update.getMessage().getText();
        switch (commandText) {
            case "/help":
                return new BotCommandHelp();
            case "/addElement":
                return new BotCommandAddElement(botService, botHandler);
            case "/removeElement":
                return new BotCommandRemoveElement(botService, botHandler);
            case "/viewTree":
                return new BotCommandViewTree(botService, botHandler);
            case "/download":
                return new BotCommandDownload(botService, botHandler);
            case "/upload":
                return new BotCommandUpload(botService, botHandler);
            default:
                return null; //команда по умолчанию
        }
    }
}
