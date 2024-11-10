package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.service.CategoryService;
import com.Pandev.pandevtelegrambot.service.ExcelService;
import com.Pandev.pandevtelegrambot.handler.BotHandler;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.ByteArrayOutputStream;

public class BotCommandDownload implements Command {
    private final CategoryService categoryService;
    private final ExcelService excelService;
    private final BotHandler botHandler;  // Инжектируем BotHandler

    public BotCommandDownload(CategoryService categoryService, ExcelService excelService, BotHandler botHandler) {
        this.categoryService = categoryService;
        this.excelService = excelService;
        this.botHandler = botHandler;
    }

    @Override
    public String execute(Update update) {
        try {
            // Генерация Excel-файла
            ByteArrayOutputStream outputStream = excelService.createExcelFile(categoryService.getAllCategories());

            // Передаем файл в BotHandler для отправки
            botHandler.sendDocument(update.getMessage().getChatId(), outputStream);

            return "Файл успешно создан и отправлен!";
        } catch (Exception e) {
            return "Ошибка при создании файла: " + e.getMessage();
        }
    }

    // Метод для создания Excel-файла
    public ByteArrayOutputStream createExcelFile() throws Exception {
        return excelService.createExcelFile(categoryService.getAllCategories());
    }
}
