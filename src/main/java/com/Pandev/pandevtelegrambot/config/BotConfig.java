package com.Pandev.pandevtelegrambot.config;

import com.Pandev.pandevtelegrambot.handler.BotHandler;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Configuration
public class BotConfig {
    private final String BOT_TOKEN = "test_intern_pandev_bot";
    private final String BOT_USERNAME = "7664107256:AAGLVuQT5AFHGp9YjeSR45eoB3e2y5POoR4";

    @Bean
    public TelegramLongPollingBot telegramBot(BotService botService) {
        return new BotHandler(botService);
    }
}
