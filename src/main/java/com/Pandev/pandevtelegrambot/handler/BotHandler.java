package com.Pandev.pandevtelegrambot.handler;

import com.Pandev.pandevtelegrambot.command.BotCommandDownload;
import com.Pandev.pandevtelegrambot.command.Command;
import com.Pandev.pandevtelegrambot.command.BotCommandFactory;
import com.Pandev.pandevtelegrambot.service.BotService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayOutputStream;

public class BotHandler extends TelegramLongPollingBot {
    private final BotService botService;

    public BotHandler(BotService botService) {
        this.botService = botService;
    }


    @Override
    public String getBotUsername() {
        return "test_intern_pandev_bot";
    }

    @Override
    public String getBotToken() {
        return "7664107256:AAGLVuQT5AFHGp9YjeSR45eoB3e2y5POoR4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Command command = BotCommandFactory.getCommand(update, botService); // Передаем botService в фабрику команд
        String responseMessage;

        if (command != null) {
            responseMessage = command.execute(update);
            if (command instanceof BotCommandDownload) {
                ByteArrayOutputStream outputStream = ((BotCommandDownload) command).createExcelFile(); // Вызов метода для создания Excel файла
                sendDocument(update.getMessage().getChatId(), outputStream);
            } else {
                sendTextResponse(update, responseMessage);
            }
        } else {
            sendTextResponse(update, "Неизвестная команда. Введите /help для получения списка команд.");
        }
    }


    private void sendTextResponse(Update update, String text) {
        SendMessage message = new SendMessage()
                .chatId(update.getMessage().getChatId())
                .text(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendDocument(Long chatId, ByteArrayOutputStream outputStream) {
        // Логика для отправки документа пользователю
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId.toString());
        sendDocument.setDocument(new InputFile(outputStream.toByteArray(), "categories.xlsx"));
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



}
