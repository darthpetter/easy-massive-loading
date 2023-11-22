package io.github.darthpetter.application.service.impl;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.darthpetter.application.service.ExcelWriting;
import io.github.darthpetter.application.utils.ExcelUtils;
import io.github.darthpetter.domain.model.annotation.MassiveLoading;
import io.github.darthpetter.domain.model.dto.HeaderNameDTO;
import io.github.darthpetter.domain.model.dto.InnerResponseDTO;

/**
 * Implementation of the {@link ExcelWriting} interface for writing data to
 * Excel files.
 */
public class ExcelWritingImpl implements ExcelWriting {

    private ExcelUtils excelUtils;

    /**
     * Constructor to initialize an instance of {@code ExcelWritingImpl}.
     * 
     * @param excelUtils The utility class for Excel operations.
     */
    public ExcelWritingImpl(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }

    /**
     * Writes data of a specified class to an Excel file.
     * 
     * @param <T>         The type of the data to be written.
     * @param targetClass The class type of the data.
     * @param dataList    The list of data objects to be written.
     * @return An {@link InnerResponseDTO} containing the Excel file data.
     */
    @Override
    public <T> InnerResponseDTO<byte[]> write(Class<T> targetClass, List<T> dataList) {
        InnerResponseDTO<byte[]> responseWrite = new InnerResponseDTO<byte[]>();
        try {
            if (targetClass.isAnnotationPresent(MassiveLoading.class)) {
                throw new Exception("Class must use annotation @MassiveLoading");
            }
            InnerResponseDTO<List<HeaderNameDTO>> responseHeaders = this.excelUtils.getHeaders(targetClass);
            if (!responseHeaders.isOk()) {
                throw new Exception(responseHeaders.getMessage());
            }
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet();
            Row headerRow = sheet.createRow(0);

            List<HeaderNameDTO> orderedHeaders = responseHeaders.getData().stream()
                    .sorted(Comparator.comparing(HeaderNameDTO::getOrder))
                    .collect(Collectors.toList());

            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);

            for (int i = 0; i < orderedHeaders.size(); i++) {
                final int index = i;
                HeaderNameDTO it = orderedHeaders.get(i);
                Cell cell = headerRow.createCell(index);
                cell.setCellValue(it.getLabel());
                cell.setCellStyle(headerCellStyle);
            }

            int rowIndex = 1;
            for (T data : dataList) {
                Row dataRow = sheet.createRow(rowIndex);

                for (int i = 0; i < orderedHeaders.size(); i++) {

                    final int index = i;
                    HeaderNameDTO it = orderedHeaders.get(i);
                    Field field = this.excelUtils.findFieldByHeader(data.getClass(), it.getVariable());

                    if (field != null) {
                        field.setAccessible(true);
                        Object value;
                        Cell cell = dataRow.createCell(index);
                        try {
                            value = field.get(data);
                            if (value != null) {
                                cell.setCellValue(value.toString());
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            cell.setCellValue("");
                            e.printStackTrace();
                        }
                    }
                }
                rowIndex++;
            }

            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                workbook.write(byteArrayOutputStream);

                byte[] archivo = byteArrayOutputStream.toByteArray();
                responseWrite.setData(archivo);
                workbook.close();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            responseWrite.setOk(false);
            responseWrite.setMessage(e.getMessage());
        }
        return responseWrite;
    }
}
