package io.github.darthpetter.example.domain.dto.persons;

import io.github.darthpetter.domain.model.annotation.Header;
import io.github.darthpetter.domain.model.annotation.MassiveLoading;
import io.github.darthpetter.example.domain.dto.Person;

@MassiveLoading
public class Programmer extends Person {
    @Header(label = "Seniority")
    private String seniority;

    @Header(label = "Programming Lenguages")
    private String[] programmingLanguages;

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public String[] getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(String[] programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

}
