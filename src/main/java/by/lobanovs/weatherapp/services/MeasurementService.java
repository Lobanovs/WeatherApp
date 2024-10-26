package by.lobanovs.weatherapp.services;


import by.lobanovs.weatherapp.models.Measurement;
import by.lobanovs.weatherapp.models.Sensor;
import by.lobanovs.weatherapp.repositories.MeasurementRepository;
import by.lobanovs.weatherapp.repositories.SensorRepository;
import by.lobanovs.weatherapp.util.Measurement.MeasurementNotCreatedException;
import by.lobanovs.weatherapp.util.Sensor.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }


    @Transactional
    public void save(Measurement measurement) {

        measurement.setCreatedAt(LocalDateTime.now());

        measurementRepository.save(measurement);
    }

    @Transactional
    public Sensor findSensorByName(String name) {
        return sensorRepository.findByName(name)
                .orElseThrow(() -> new SensorNotFoundException("Сенсор не найден"));
    }

}
