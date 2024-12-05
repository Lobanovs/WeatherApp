package by.lobanovs.weatherapp.dto;

import by.lobanovs.weatherapp.models.Sensor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {

    @Column(name = "value")
    @DecimalMin(value = "-100.0", inclusive = true, message = "Значение не может быть меньше -100.0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Значение не может быть больше 100.0")
    @NotNull(message = "Температура не может быть пустой")
    private Double value;


    @Column(name= "raining")
    @NotNull(message = "дождь либо идет, либо нет")
    private Boolean raining; // Измените на Boolean

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;


    public @DecimalMin(value = "-100.0", inclusive = true, message = "Значение не может быть меньше -100.0") @DecimalMax(value = "100.0", inclusive = true, message = "Значение не может быть больше 100.0") @NotNull(message = "Температура не может быть пустой") Double getValue() {
        return value;
    }

    public void setValue(@DecimalMin(value = "-100.0", inclusive = true, message = "Значение не может быть меньше -100.0") @DecimalMax(value = "100.0", inclusive = true, message = "Значение не может быть больше 100.0") @NotNull(message = "Температура не может быть пустой") Double value) {
        this.value = value;
    }

    public @NotNull(message = "дождь либо идет, либо нет") Boolean getRaining() {
        return raining;
    }

    public void setRaining(@NotNull(message = "дождь либо идет, либо нет") Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
