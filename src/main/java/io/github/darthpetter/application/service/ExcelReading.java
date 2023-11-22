package io.github.darthpetter.application.service;

import java.io.InputStream;
import java.util.List;

import io.github.darthpetter.domain.model.dto.InnerResponseDTO;

/**
 * Interface for reading data from Excel files.
 */
public interface ExcelReading {

    /**
     * Reads data from an Excel file into a list of objects of the specified class.
     *
     * @param <T>              The type of objects to be read from Excel.
     * @param excelInputStream The input stream of the Excel file.
     * @param targetClass      The class type of the objects to be read.
     * @return An {@link InnerResponseDTO} containing the list of objects read from
     *         the Excel file.
     */
    <T> InnerResponseDTO<List<T>> read(InputStream excelInputStream, Class<T> targetClass);
}
