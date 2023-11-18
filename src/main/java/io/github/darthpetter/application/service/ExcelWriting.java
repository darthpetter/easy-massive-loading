package io.github.darthpetter.application.service;

import java.util.List;

import io.github.darthpetter.domain.model.dto.InnerResponseDTO;

public interface ExcelWriting {
    public <T> InnerResponseDTO<byte[]> write(Class<T> targetClass, List<T> dataList);
}
