package by.lobanovs.weatherapp;

import by.lobanovs.weatherapp.models.Measurement;
import by.lobanovs.weatherapp.models.Sensor;
import by.lobanovs.weatherapp.repositories.MeasurementRepository;
import by.lobanovs.weatherapp.repositories.SensorRepository;
import by.lobanovs.weatherapp.services.MeasurementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementServiceTest {
    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private MeasurementService measurementService;

    @Test
    void testSaveMeasurement() {
        Measurement measurement = new Measurement();
        measurement.setValue(25.0);
        measurement.setRaining(true);

        measurementService.save(measurement);

        verify(measurementRepository, times(1)).save(measurement);
        assertNotNull(measurement.getCreatedAt());
    }

    @Test
    void testFindSensorByName() {
        Sensor sensor = new Sensor();
        sensor.setName("TestSensor");

        when(sensorRepository.findByName("TestSensor")).thenReturn(Optional.of(sensor));

        Sensor result = measurementService.findSensorByName("TestSensor");

        assertEquals("TestSensor", result.getName());
    }
}
