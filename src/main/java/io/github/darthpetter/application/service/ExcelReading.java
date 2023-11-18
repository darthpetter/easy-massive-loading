package io.github.darthpetter.application.service;

import java.io.InputStream;
import java.util.List;

import io.github.darthpetter.domain.model.dto.InnerResponseDTO;

public interface ExcelReading {
    public <T> InnerResponseDTO<List<T>> read(InputStream excelInputStream, Class<T> targetClass);
}
