package com.Pandev.pandevtelegrambot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
//Скрыл с общего доступа токен и имя бота с помощью BotProperties
@ConfigurationProperties(prefix = "bot")
public record BotProperties(String name, String token) {
}
