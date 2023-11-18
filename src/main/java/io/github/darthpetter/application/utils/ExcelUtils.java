package io.github.darthpetter.application.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import io.github.darthpetter.domain.model.annotation.Header;
import io.github.darthpetter.domain.model.dto.HeaderNameDTO;
import io.github.darthpetter.domain.model.dto.InnerResponseDTO;

public class ExcelUtils {
    public <T> InnerResponseDTO<List<HeaderNameDTO>> getHeaders(Class<T> targetClass) {
        InnerResponseDTO<List<HeaderNameDTO>> responseDTO = new InnerResponseDTO<>();
        List<HeaderNameDTO> headers = new ArrayList<>();
        try {
            Field[] fields = targetClass.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Header.class)) {

                    Header headerAnnotation = field.getAnnotation(Header.class);

                    String label = headerAnnotation.label();
                    if (label.isEmpty()) {
                        label = field.getName();
                    }

                    HeaderNameDTO headerNameDTO = new HeaderNameDTO();
                    headerNameDTO.setOrder(headerAnnotation.order());
                    headerNameDTO.setLabel(label);
                    headerNameDTO.setVariable(field.getName());
                    headers.add(headerNameDTO);
                }
            }
            if (this.hasDuplicates(headers)) {
                throw new Exception("Class contains duplicates labels");
            }
            responseDTO.setData(headers);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setOk(false);
            responseDTO.setMessage(e.getMessage());
        }
        return responseDTO;
    }

    public Field findFieldByHeader(Class<?> clazz, String header) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Header.class) && field.getName().equals(header)) {
                return field;
            }
        }
        return null;
    }

    public boolean isEmptyRow(Row row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDuplicates(List<HeaderNameDTO> headers) {
        Map<String, HeaderNameDTO> map = headers.stream()
                .collect(Collectors.toMap(HeaderNameDTO::getLabel, Function.identity(),
                        (existing, replacement) -> existing));

        return map.values().stream()
                .anyMatch(dto -> dto != null && Collections.frequency(map.keySet(), dto.getLabel()) > 1);
    }
}
