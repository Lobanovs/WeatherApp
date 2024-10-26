package by.lobanovs.weatherapp.dto;

import by.lobanovs.weatherapp.models.Sensor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

public class MeasurementsDTO {

    @Column(name = "value")
    @DecimalMin(value = "-100.0", inclusive = true, message = "Значение не может быть меньше -100.0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Значение не может быть больше 100.0")
    @NotEmpty(message = "Температура не может быть пустой")
    private double value;


    @Column(name= "raining")
    @NotEmpty(message = "Дождь либо идет, либо не идет")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;



    @DecimalMin(value = "-100.0", inclusive = true, message = "Значение не может быть меньше -100.0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Значение не может быть больше 100.0")
    @NotEmpty(message = "Температура не может быть пустой")
    public double getValue() {
        return value;
    }

    public void setValue(@DecimalMin(value = "-100.0", inclusive = true, message = "Значение не может быть меньше -100.0") @DecimalMax(value = "100.0", inclusive = true, message = "Значение не может быть больше 100.0") @NotEmpty(message = "Температура не может быть пустой") double value) {
        this.value = value;
    }

    @NotEmpty(message = "Дождь либо идет, либо не идет")
    public boolean isRaining() {
        return raining;
    }

    public void setRaining(@NotEmpty(message = "Дождь либо идет, либо не идет") boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
