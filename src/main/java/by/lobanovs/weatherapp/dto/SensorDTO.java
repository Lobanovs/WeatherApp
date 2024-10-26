package by.lobanovs.weatherapp.dto;


import by.lobanovs.weatherapp.models.Measurements;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.util.List;

public class SensorDTO {

    @Column(name="name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
