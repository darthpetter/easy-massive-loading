package io.github.darthpetter.example.application.services.impl;

import java.io.InputStream;
import java.util.List;

import io.github.darthpetter.application.service.impl.ExcelReadingImpl;
import io.github.darthpetter.domain.model.dto.InnerResponseDTO;
import io.github.darthpetter.example.application.services.ProgrammerService;
import io.github.darthpetter.example.domain.dto.persons.Programmer;

public class ProgrammerServiceImpl implements ProgrammerService {

    private ExcelReadingImpl excelReading;

    public ProgrammerServiceImpl() {
        this.excelReading = new ExcelReadingImpl();
    }

    @Override
    public List<Programmer> readFile(InputStream fileInputStream) {
        InnerResponseDTO<List<Programmer>> readingResponse = this.excelReading.read(fileInputStream, Programmer.class);
        if (!readingResponse.isOk()) {
            return null;
        }
        return readingResponse.getData();
    }

}
