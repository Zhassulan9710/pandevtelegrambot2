package com.Pandev.pandevtelegrambot.handler;

import com.Pandev.pandevtelegrambot.command.BotCommandFactory;
import com.Pandev.pandevtelegrambot.command.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BotHandler extends TelegramLongPollingBot {
    private final BotCommandFactory botCommandFactory;

    @Value("${bot.token}")
    private String botToken;

    public BotHandler(BotCommandFactory botCommandFactory) {
        this.botCommandFactory = botCommandFactory;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            try {
                // Получаем команду через фабрику команд
                Command command = BotCommandFactory.getCommand(messageText);
                String response = command != null
                        ? command.execute(update)
                        : "Неизвестная команда. Введите /help для получения списка доступных команд.";

                // Отправляем ответ пользователю
                sendMessage(chatId, response);
            } catch (Exception e) {
                sendMessage(chatId, "Произошла ошибка: " + e.getMessage());
            }
        }
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);

        try {
            execute(sendMessage); // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return "test_intern_pandev_bot";
    }
    @Override
    public String getBotToken() {
        return botToken;
    }
}
