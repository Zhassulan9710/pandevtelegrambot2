package com.Pandev.pandevtelegrambot.handler;

import com.Pandev.pandevtelegrambot.command.BotCommandDownload;
import com.Pandev.pandevtelegrambot.command.Command;
import com.Pandev.pandevtelegrambot.command.BotCommandFactory;
import com.Pandev.pandevtelegrambot.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
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
        Command command = BotCommandFactory.getCommand(update);
        String responseMessage;

        if (command != null) {
            responseMessage = command.execute(update);
            if (command instanceof BotCommandDownload) {
                ByteArrayOutputStream outputStream = null;
                try {
                    outputStream = ((BotCommandDownload) command).createExcelFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
                .wait(update.getMessage().getChatId())
                .text(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке текстового сообщения: ", e);
        }
    }

    private void sendDocument(Long chatId, ByteArrayOutputStream outputStream) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId.toString());
        sendDocument.setDocument(new InputFile(outputStream.toByteArray(), "categories.xlsx"));
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке документа: ", e);
        }
    }
}
