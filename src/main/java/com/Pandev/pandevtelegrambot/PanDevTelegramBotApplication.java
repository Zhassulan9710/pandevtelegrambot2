package com.Pandev.pandevtelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// Основной класс бота
@ConfigurationPropertiesScan
@SpringBootApplication
public class PanDevTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanDevTelegramBotApplication.class, args);
	}
}
