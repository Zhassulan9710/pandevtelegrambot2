package com.Pandev.pandevtelegrambot.command;
import com.Pandev.pandevtelegrambot.model.BotCategory;
import com.Pandev.pandevtelegrambot.service.BotService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BotCommandUpload implements Command {
    private final BotService botService;

    public BotCommandUpload(BotService botService) {
        this.botService = botService;
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
            List<BotCategory> categories = parseExcelFile(inputStream);
            botService.updateCategories(categories);
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

    private List<BotCategory> parseExcelFile(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<BotCategory> categories = new ArrayList<>();

        for (Row row : sheet) {
            String categoryName = row.getCell(0).getStringCellValue();
            BotCategory category = new BotCategory(categoryName);
            categories.add(category);
        }

        workbook.close();
        return categories;
    }
}
