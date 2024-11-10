package com.Pandev.pandevtelegrambot.config;

import com.Pandev.pandevtelegrambot.handler.BotHandler;
import com.Pandev.pandevtelegrambot.properties.BotProperties;
import com.Pandev.pandevtelegrambot.service.CategoryService;
import com.Pandev.pandevtelegrambot.service.ExcelService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Configuration
public class BotConfig {
    @Bean
    public TelegramLongPollingBot telegramBot(BotProperties botProperties, CategoryService categoryService, ExcelService excelService) {
        return new BotHandler(botProperties, categoryService, excelService);
    }
}

