package by.lobanovs.weatherapp.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @Column(name="name")
    @NotBlank(message = "имя сенсора не должно быть пустым")
    @Size(min=3, max = 30, message = "Имя сенсора должно быть от 3 до 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
