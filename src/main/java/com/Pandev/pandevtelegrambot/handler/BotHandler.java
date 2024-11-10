package com.Pandev.pandevtelegrambot.handler;

import com.Pandev.pandevtelegrambot.command.BotCommandDownload;
import com.Pandev.pandevtelegrambot.command.Command;
import com.Pandev.pandevtelegrambot.command.BotCommandFactory;
import com.Pandev.pandevtelegrambot.properties.BotProperties;
import com.Pandev.pandevtelegrambot.service.CategoryService;
import com.Pandev.pandevtelegrambot.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@Slf4j
public class BotHandler extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final BotCommandFactory botCommandFactory;

    public BotHandler(BotProperties botProperties, CategoryService categoryService, ExcelService excelService) {
        this.botProperties = botProperties;
        this.botCommandFactory = new BotCommandFactory(categoryService, excelService, this); // Включаем BotHandler в фабрику команд
    }

    @Override
    public String getBotUsername() {
        return botProperties.name();
    }

    @Override
    public String getBotToken() {
        return botProperties.token();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Command command = botCommandFactory.getCommand(update);
        String responseMessage;

        if (command != null) {
            responseMessage = command.execute(update);
            if (command instanceof BotCommandDownload) {
                try {
                    // Получаем и отправляем файл
                    ByteArrayOutputStream outputStream = ((BotCommandDownload) command).createExcelFile();
                    sendDocument(update.getMessage().getChatId(), outputStream);
                } catch (Exception e) {
                    log.error("Ошибка при создании Excel файла: ", e);
                    sendTextResponse(update, "Произошла ошибка при создании документа.");
                }
            } else {
                sendTextResponse(update, responseMessage);
            }
        } else {
            sendTextResponse(update, "Неизвестная команда. Введите /help для получения списка команд.");
        }
    }

    // Отправка документа
    public void sendDocument(Long chatId, ByteArrayOutputStream outputStream) {
        InputFile inputFile = new InputFile(new ByteArrayInputStream(outputStream.toByteArray()), "categories.xlsx");
        SendDocument sendDocument = new SendDocument(chatId.toString(), inputFile);
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке документа: ", e);
        }
    }

    // Отправка текстового сообщения
    private void sendTextResponse(Update update, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке текстового сообщения: ", e);
        }
    }
}
