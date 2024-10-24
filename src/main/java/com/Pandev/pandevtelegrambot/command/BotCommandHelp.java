package com.Pandev.pandevtelegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandHelp implements Command {
    @Override
    public String execute(Update update) {
        return """
                Доступные команды:
                /help - помощь
                /addElement <название элемента> - добавить элемент
                /removeElement <название элемента> - удалить элемент
                /viewTree - показать дерево категорий
                /download - скачать дерево в Excel
                /upload - загрузить дерево из Excel""";
    }
}
