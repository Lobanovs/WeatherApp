package by.lobanovs.weatherapp.senderAndClient;

import by.lobanovs.weatherapp.dto.MeasurementDTO;
import by.lobanovs.weatherapp.models.Sensor;
import by.lobanovs.weatherapp.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class MeasurementSender {


    private final RestTemplate restTemplate;
    private final MeasurementService measurementService;



    @Autowired
    public MeasurementSender(RestTemplate restTemplate, MeasurementService measurementService) {
        this.restTemplate = restTemplate;
        this.measurementService = measurementService;
    }

    private static final String ADD_MEASUREMENT_URL = "http://localhost:8080/measurements/add";


    public void sendMeasurements() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            // Генерация случайной температуры в диапазоне от -100 до 100
            double temperature = -100 + (200 * random.nextDouble());
            // Генерация случайного значения для дождя
            boolean raining = random.nextBoolean();

            String sensorName = "1"; // Название сенсора, которое вы хотите использовать
            Sensor existingSensor = measurementService.findSensorByName(sensorName);

            // Создание JSON-объекта для отправки
            MeasurementDTO measurementDTO = new MeasurementDTO();
            measurementDTO.setValue(temperature);
            measurementDTO.setRaining(raining);
            measurementDTO.setSensor(existingSensor); // ваш объект сенсора

            // Отправка POST-запроса
            try {
                restTemplate.postForEntity(ADD_MEASUREMENT_URL, measurementDTO, String.class);
                System.out.println("Sent measurement: " + measurementDTO);
            } catch (Exception e) {
                System.out.println("Error sending measurement: " + e.getMessage());
            }
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendMeasurementsEveryTime() {
        Random random = new Random();

        // Генерация случайной температуры в диапазоне от -100 до 100
        double temperature = -1 + (2 * random.nextDouble());
        // Генерация случайного значения для дождя
        boolean raining = random.nextBoolean();

        String sensorName = "1"; // Название сенсора
        Sensor existingSensor = measurementService.findSensorByName(sensorName);

        // Создание JSON-объекта для отправки
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setValue(temperature);
        measurementDTO.setRaining(raining);
        measurementDTO.setSensor(existingSensor);

        // Отправка POST-запроса
        try {
            restTemplate.postForEntity(ADD_MEASUREMENT_URL, measurementDTO, String.class);
            System.out.println("Sent measurement: " + measurementDTO);
        } catch (Exception e) {
            System.out.println("Error sending measurement: " + e.getMessage());
        }
    }
}