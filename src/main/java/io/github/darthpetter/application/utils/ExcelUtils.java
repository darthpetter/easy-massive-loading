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

/**
 * Utility class for Excel-related operations.
 */
public class ExcelUtils {

    /**
     * Retrieves header information from the given class using the Header
     * annotation.
     *
     * @param <T>         The type of the class.
     * @param targetClass The class from which to retrieve header information.
     * @return An {@link InnerResponseDTO} containing a list of
     *         {@link HeaderNameDTO}.
     */
    public <T> InnerResponseDTO<List<HeaderNameDTO>> getHeaders(Class<T> targetClass) {
        InnerResponseDTO<List<HeaderNameDTO>> responseDTO = new InnerResponseDTO<>();
        List<HeaderNameDTO> headers = new ArrayList<>();
        try {
            Field[] fields=this.getAllFields(targetClass);

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

    /**
     * Finds the field in a class based on the provided header label.
     *
     * @param clazz  The class to search for the field.
     * @param header The header label to match.
     * @return The {@link Field} corresponding to the header label, or null if not
     *         found.
     */
    public Field findFieldByHeader(Class<?> clazz, String header) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Header.class) && field.getName().equals(header)) {
                return field;
            }
        }
        return null;
    }

    /**
     * Checks if a given row in an Excel sheet is empty.
     *
     * @param row The row to check for emptiness.
     * @return true if the row is empty, false otherwise.
     */
    public boolean isEmptyRow(Row row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    // Private method to check for duplicate labels
    private boolean hasDuplicates(List<HeaderNameDTO> headers) {
        Map<String, HeaderNameDTO> map = headers.stream()
                .collect(Collectors.toMap(HeaderNameDTO::getLabel, Function.identity(),
                        (existing, replacement) -> existing));

        return map.values().stream()
                .anyMatch(dto -> dto != null && Collections.frequency(map.keySet(), dto.getLabel()) > 1);
    }

    private Field[] getAllFields(Class<?> targetClass) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = targetClass;

        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                fields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }

        return fields.toArray(new Field[0]);
    }
}
