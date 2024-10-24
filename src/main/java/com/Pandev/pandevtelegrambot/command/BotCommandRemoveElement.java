package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.handler.BotHandler;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandRemoveElement implements Command {
    private final BotService botService;

    public BotCommandRemoveElement(BotService botService, BotHandler botHandler) {
        this.botService = botService;
    }

    @Override
    public String execute(Update update) {
        String[] commandParts = update.getMessage().getText().split(" ");
        if (commandParts.length != 2) {
            return "Неверный формат команды. Используйте: /removeElement <название элемента>";
        }
        String categoryName = commandParts[1];
        boolean removed = botService.removeCategory(categoryName);
        return removed ? "Элемент \"" + categoryName + "\" удален." : "Элемент \"" + categoryName + "\" не найден.";
    }
}
