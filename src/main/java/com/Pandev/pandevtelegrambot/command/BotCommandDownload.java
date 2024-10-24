package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.handler.BotHandler;
import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BotCommandDownload implements Command{
    private final BotService botService;

    public BotCommandDownload(BotService botService, BotHandler botHandler) {
        this.botService = botService;
    }

    @Override
    public String execute(Update update) {
        try {
            ByteArrayOutputStream outputStream = createExcelFile();
            // Логика отправки файла пользователю
            return "Файл успешно создан.";
        } catch (IOException e) {
            return "Ошибка при создании файла: " + e.getMessage();
        }
    }

    private ByteArrayOutputStream createExcelFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Categories");

        int rowNum = 0;
        for (BotCategory category : botService.getAllCategories()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(category.getName());
            // Добавьте логику для добавления дочерних элементов, если необходимо
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }
}

