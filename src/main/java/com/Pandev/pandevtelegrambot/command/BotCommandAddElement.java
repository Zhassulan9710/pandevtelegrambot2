package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandAddElement implements Command {
    private final BotService botService;

    public BotCommandAddElement(BotService botService) {
        this.botService = botService;
    }

    @Override
    public String execute(Update update) {
        String[] commandParts = update.getMessage().getText().split(" ");
        if (commandParts.length != 2) {
            return "Неверный формат команды. Используйте: /addElement <название элемента>";
        }
        String categoryName = commandParts[1];
        BotCategory category = new BotCategory(categoryName);
        botService.addCategory(category);
        return "Элемент \"" + categoryName + "\" добавлен.";
    }
}
