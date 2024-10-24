package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandViewTree implements Command{

    private final BotService botService;

    public BotCommandViewTree(BotService botService) {
        this.botService = botService;
    }

    @Override
    public String execute(Update update) {
        return botService.viewCategoriesTree();
    }
}
