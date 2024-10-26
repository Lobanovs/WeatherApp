package by.lobanovs.weatherapp.services;

import by.lobanovs.weatherapp.models.Sensor;
import by.lobanovs.weatherapp.repositories.SensorRepository;
import by.lobanovs.weatherapp.util.SensorNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {


    private SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public boolean isSensorNameExist(String sensorName) {
        return sensorRepository.existsByName(sensorName);
    }

    public void save(Sensor sensor) {
        // Валидация, чтобы убедиться, что сенсора с таким именем нет в БД
        if (sensorRepository.existsByName(sensor.getName())) {
            throw new SensorNotCreatedException("Сенсор с именем '" + sensor.getName() + "' уже существует.");
        }

        sensorRepository.save(sensor);
    }
}
