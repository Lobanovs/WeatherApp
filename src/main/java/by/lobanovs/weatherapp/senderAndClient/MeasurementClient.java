package by.lobanovs.weatherapp.senderAndClient;

import by.lobanovs.weatherapp.dto.MeasurementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MeasurementClient {


    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080"; // Замените на URL вашего сервера

    public List<MeasurementDTO> getMeasurements() {
        String url = BASE_URL + "/measurements"; // Полный URL
        MeasurementDTO[] measurements = restTemplate.getForObject(url, MeasurementDTO[].class);
        return List.of(measurements); // Преобразуем массив в список
    }
}
