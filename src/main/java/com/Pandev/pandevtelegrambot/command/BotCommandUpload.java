package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.service.CategoryService;
import com.Pandev.pandevtelegrambot.service.ExcelService;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.List;

public class BotCommandUpload implements Command {
    private final CategoryService categoryService;
    private final ExcelService excelService;

    public BotCommandUpload(CategoryService categoryService, ExcelService excelService) {
        this.categoryService = categoryService;
        this.excelService = excelService;
    }

    @Override
    public String execute(Update update) {
        Message message = update.getMessage();
        Document document = message.getDocument();

        if (document == null) {
            return "Пожалуйста, загрузите файл формата Excel.";
        }

        try {
            InputStream inputStream = downloadFile(document);
            List<BotCategory> categories = excelService.parseExcelFile(inputStream);
            categoryService.updateCategories(categories);
            return "Данные категорий успешно загружены.";
        } catch (Exception e) {
            return "Ошибка при загрузке файла: " + e.getMessage();
        }
    }

    // Получение потока данных из загруженного файла
    private InputStream downloadFile(Document document) throws TelegramApiException {
        String fileId = document.getFileId();
        return new InputFile(fileId).getNewMediaStream();
    }
}
