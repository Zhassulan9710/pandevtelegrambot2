package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandRemoveElement implements Command {
    private final BotService botService;

    public BotCommandRemoveElement(BotService botService) {
        this.botService = botService;
    }
    @Override
    public String execute(Update update) {
        try {
            String name = update.getMessage().getText().split(" ")[1];
            botService.removeCategory(name);
            return "Элемент удален: " + name;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Ошибка при удалении элемента: " + e.getMessage();
        }
    }
}
