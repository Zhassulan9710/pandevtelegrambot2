package com.Pandev.pandevtelegrambot.command;

import com.Pandev.pandevtelegrambot.handler.BotHandler;
import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class BotCommandDownload implements Command{
    private final BotService botService;
    private final BotHandler botHandler;

    public BotCommandDownload(BotService botService, BotHandler botHandler) {
        this.botService = botService;
        this.botHandler = botHandler;
    }

    @Override
    public String execute(Update update) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Categories");
            int rowCount = 0;

            for (BotCategory botCategory : botService.getAllCategories()) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(botCategory.getName());
                rowCount = addChildrenToSheet(sheet, botCategory, rowCount);
            }

            // Создаем файл Excel в виде потока
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            // Отправляем файл пользователю
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(update.getMessage().getChatId().toString());
            sendDocument.setDocument(new InputFile(inputStream, "categories.xlsx"));

            try {
                botHandler.execute(sendDocument); // Отправляем документ через бота
                return "Файл с деревом категорий успешно создан и отправлен.";
            } catch (TelegramApiException e) {
                return "Ошибка при отправке файла: " + e.getMessage();
            }
        } catch (Exception e) {
            return "Ошибка при создании Excel файла: " + e.getMessage();
        }
    }
    private int addChildrenToSheet(Sheet sheet, BotCategory category, int rowCount) {
        for (BotCategory child : category.getChildren()) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(child.getName());
            rowCount = addChildrenToSheet(sheet, child, rowCount); // Рекурсивно добавляем дочерние категории
        }
        return rowCount;
    }
}
