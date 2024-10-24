package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.handler.BotHandler;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandUpload implements Command {
    public BotCommandUpload(BotService botService, BotHandler botHandler) {
    }

    @Override

    public String execute(Update update) {
        // Логика загрузки дерева категорий из Excel (опционально)
        return "Загрузка из Excel еще не реализована.";
    }
}
