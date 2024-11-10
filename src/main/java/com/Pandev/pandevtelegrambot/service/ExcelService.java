package com.Pandev.pandevtelegrambot.service;

import com.Pandev.pandevtelegrambot.model.BotCategory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
// Убрал логику загрузки и выгрузки Эксель файла в отдельный класс
@Service
public class ExcelService {

    // Метод для создания Excel-файла из списка категорий
    public ByteArrayOutputStream createExcelFile(List<BotCategory> categories) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Categories");

        int rowNum = 0;
        for (BotCategory category : categories) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(category.getName());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }

    // Метод для чтения Excel-файла и преобразования его в список категорий
    public List<BotCategory> parseExcelFile(InputStream inputStream) throws Exception {
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
