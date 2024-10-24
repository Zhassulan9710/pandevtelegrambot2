package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandFactory {
    private final BotService botService;

    public BotCommandFactory(BotService botService) {
        this.botService = botService;
    }

    public Command getCommand(Update update) {
        String commandText = update.getMessage().getText();
        switch (commandText) {
            case "/help":
                return new BotCommandHelp();
            case "/addElement":
                return new BotCommandAddElement(botService);
            case "/removeElement":
                return new BotCommandRemoveElement(botService);
            case "/viewTree":
                return new BotCommandViewTree(botService);
            case "/download":
                return new BotCommandDownload(botService);
            case "/upload":
                return new BotCommandUpload(botService);
            default:
                return null;
        }
    }
}

