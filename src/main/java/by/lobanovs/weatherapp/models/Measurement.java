package by.lobanovs.weatherapp.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Measurements")
public class Measurement {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @DecimalMin(value = "-100.0",  message = "Значение не может быть меньше -100.0")
    @DecimalMax(value = "100.0",  message = "Значение не может быть больше 100.0")
    @NotNull(message = "Температура не может быть пустой")
    private Double value;


    @Column(name= "raining")
    @NotNull(message = "дождь либо идет, либо нет")
    private Boolean raining; // Измените на Boolean

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Measurement(double value, boolean raining, Sensor sensor, LocalDateTime createdAt) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.createdAt = createdAt;
    }

    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
