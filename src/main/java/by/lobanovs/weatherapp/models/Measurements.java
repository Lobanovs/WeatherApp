package by.lobanovs.weatherapp.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Measurements")
public class Measurements {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Measurements(double value, boolean raining, Sensor sensor, LocalDateTime createdAt) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.createdAt = createdAt;
    }

    public Measurements() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
