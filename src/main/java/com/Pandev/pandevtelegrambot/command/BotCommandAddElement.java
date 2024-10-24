package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.service.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandAddElement implements Command {
    private final BotService botService;

    public BotCommandAddElement(BotService botService) {
        this.botService = botService;
    }

    @Override
    public String execute(Update update) {
        try {
            String[] parts = update.getMessage().getText().split(" ");
            if (parts.length == 2) {
                String name = parts[1];
                botService.addCategory(name, null); // Корневой элемент
                return "Элемент добавлен: " + name;
            } else if (parts.length == 3) {
                String parentName = parts[1];
                String childName = parts[2];
                botService.addCategory(childName, parentName);
                return "Дочерний элемент добавлен: " + childName + " к родительскому элементу: " + parentName;
            }
            return "Неверный формат команды. Используйте: /addElement <название> или /addElement <родительский> <дочерний>";
        } catch (Exception e) {
            return "Ошибка при добавлении элемента: " + e.getMessage();
        }
    }
}
