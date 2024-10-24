package com.Pandev.pandevtelegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandHelp implements Command {
    @Override
    public String execute(Update update) {
        return """
                Доступные команды:
                /viewTree - Просмотреть дерево категорий
                /addElement <название> - Добавить элемент
                /removeElement <название> - Удалить элемент
                /download - Скачивание дерева категорий в Excel
                /upload - Загрузка дерева категорий из Excel
                /help - Показать список команд""";
    }

}
