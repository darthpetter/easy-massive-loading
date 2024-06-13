package io.github.darthpetter.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.darthpetter.application.service.impl.ExcelWritingImpl;
import io.github.darthpetter.domain.model.dto.InnerResponseDTO;
import io.github.darthpetter.example.domain.dto.persons.Programmer;

public class ExampleApp {
    public static void main(String[] args) {
        try {
            ExcelWritingImpl excelService = new ExcelWritingImpl();
            InnerResponseDTO<byte[]> responseFile = excelService.write(Programmer.class, null);
            if (!responseFile.isOk()) {
                throw new Exception(responseFile.getMessage());
            }
            Path path = Paths.get("/home/ubuntu/excel.xlsx");
            Files.write(path, responseFile.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
