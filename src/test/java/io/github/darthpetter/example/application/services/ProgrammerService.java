package io.github.darthpetter.example.application.services;

import java.io.InputStream;
import java.util.List;

import io.github.darthpetter.example.domain.dto.persons.Programmer;

public interface ProgrammerService {
    public List<Programmer> readFile(InputStream fileInputStream);
} 
