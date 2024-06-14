package io.github.darthpetter.application.service;

import java.util.List;

import io.github.darthpetter.domain.model.dto.InnerResponseDTO;

/**
 * Interface for writing data to Excel files.
 */
public interface IExcelWritingService {

    /**
     * Writes data of a specified class to an Excel file.
     *
     * @param <T>         The type of the data to be written.
     * @param targetClass The class type of the data.
     * @param dataList    The list of data objects to be written.
     * @return An {@link InnerResponseDTO} containing the Excel file data.
     */
    <T> InnerResponseDTO<byte[]> write(Class<T> targetClass, List<T> dataList);
}
