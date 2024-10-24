package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.service.BotService;

public class BotCommandFactory {
    private final BotService botService;

    public BotCommandFactory (BotService botService){
        this.botService = botService;
    }
    public Command getCommand(String commandText) {
        if (commandText.equals("/viewTree")) {
            return new BotCommandViewTree(botService);
        } else if (commandText.startsWith("/addElement")) {
            return new BotCommandAddElement(botService);
        } else if (commandText.startsWith("/removeElement")) {
            return new BotCommandRemoveElement(botService);
        } else if (commandText.equals("/help")) {
            return new BotCommandHelp();
        } else if (commandText.equals("/download")) {
            return new BotCommandDownload(botService);
        } else if (commandText.equals("/upload")) {
            return new BotCommandUpload(botService);
        }
        return null; // неизвестная команда
    }
}
