package io.github.darthpetter.application.service.impl;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import io.github.darthpetter.application.service.ExcelReading;
import io.github.darthpetter.application.utils.ExcelUtils;
import io.github.darthpetter.domain.model.dto.HeaderNameDTO;
import io.github.darthpetter.domain.model.dto.InnerResponseDTO;

public class ExcelReadingImpl implements ExcelReading {
    private ExcelUtils excelUtils;

    public ExcelReadingImpl(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }

    public <T> InnerResponseDTO<List<T>> read(InputStream fileInputStream, Class<T> targetClass) {
        InnerResponseDTO<List<T>> response = new InnerResponseDTO<>();
        List<T> dataList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue());
                }

                InnerResponseDTO<List<HeaderNameDTO>> responseHeaders = this.excelUtils.getHeaders(targetClass);
                if (!responseHeaders.isOk()) {
                    throw new Exception(responseHeaders.getMessage());
                }

                List<HeaderNameDTO> headersPresents = responseHeaders.getData().stream()
                        .filter(it -> headers.contains(it.getLabel())).collect(Collectors.toList());

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (!this.excelUtils.isEmptyRow(row)) {
                        T instance = targetClass.getDeclaredConstructor().newInstance();

                        for (int i = 0; i < headersPresents.size(); i++) {
                            Cell cell = row.getCell(i);
                            HeaderNameDTO headerNameDTO = headersPresents.get(i);

                            if (cell != null) {
                                Field field = instance.getClass().getDeclaredField(headerNameDTO.getVariable());
                                field.setAccessible(true);

                                Class<?> fieldType = field.getType();
                                try {
                                    if (fieldType == String.class) {
                                        field.set(instance, cell.getStringCellValue());
                                    } else if (fieldType == int.class || fieldType == Integer.class) {
                                        field.set(instance, (int) cell.getNumericCellValue());
                                    } else if (fieldType == double.class || fieldType == Double.class) {
                                        field.set(instance, cell.getNumericCellValue());
                                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                                        field.set(instance, cell.getBooleanCellValue());
                                    }
                                } catch (Exception e) {
                                    throw new Exception(
                                            "Error on line " + (row.getRowNum() + 1) + " " + e.getMessage());
                                }
                            }
                        }
                        dataList.add(instance);
                    }
                }
            }
            response.setData(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            response.setOk(false);
            response.setCode("ERROR");
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
