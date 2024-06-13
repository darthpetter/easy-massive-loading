package io.github.darthpetter.example.domain.dto;

import io.github.darthpetter.domain.model.annotation.Header;
import io.github.darthpetter.domain.model.annotation.MassiveLoading;

@MassiveLoading
public class Person {
    @Header(label = "First Name")
    private String firstName;
    
    @Header(label = "Last Name")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
}
