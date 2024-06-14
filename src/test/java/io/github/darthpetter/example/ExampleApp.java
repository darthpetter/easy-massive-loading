package io.github.darthpetter.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import io.github.darthpetter.application.service.impl.XLSXReadingService;
import io.github.darthpetter.application.service.impl.XLSXWritingService;
import io.github.darthpetter.domain.model.dto.InnerResponseDTO;
import io.github.darthpetter.example.domain.dto.persons.Programmer;

public class ExampleApp {
    public static void main(String[] args) {
        try {
            XLSXWritingService excelWritingService = new XLSXWritingService();
            InnerResponseDTO<byte[]> responseFile = excelWritingService.write(Programmer.class, null);
            if (!responseFile.isOk()) {
                throw new Exception(responseFile.getMessage());
            }
            Path path = Paths.get("/app/resources/files/excel_write.xlsx");
            Files.write(path, responseFile.getData());

            Path pathForReading = Paths.get("/app/resources/files/excel_read.xlsx");
            XLSXReadingService excelReadingService = new XLSXReadingService();
            FileInputStream fileInputStream = new FileInputStream(pathForReading.toFile());
            InnerResponseDTO<List<Programmer>> responseReadedFile = excelReadingService.read(fileInputStream,
                    Programmer.class);
            if (!responseReadedFile.isOk()) {
                throw new Exception(responseReadedFile.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
