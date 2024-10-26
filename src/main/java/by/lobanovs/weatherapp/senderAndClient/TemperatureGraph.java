package by.lobanovs.weatherapp.senderAndClient;

import by.lobanovs.weatherapp.dto.MeasurementDTO;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;
import org.springframework.web.client.RestTemplate;
import org.knowm.xchart.BitmapEncoder;


import java.io.IOException;
import java.util.List;

public class TemperatureGraph {

    private static final String MEASUREMENTS_URL = "http://localhost:8080/measurements"; // URL вашего сервера

    public static void main(String[] args) {
        // Создаем экземпляр RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Получаем данные с сервера
        List<MeasurementDTO> measurements = List.of(restTemplate.getForObject(MEASUREMENTS_URL, MeasurementDTO[].class));

        // Создаем график
        XYChart chart = new XYChart(800, 600);
        chart.setTitle("График Температуры");
        chart.setXAxisTitle("Индекс измерения");
        chart.setYAxisTitle("Температура (°C)");

        // Добавляем данные в график
        double[] xData = new double[measurements.size()];
        double[] yData = new double[measurements.size()];

        for (int i = 0; i < measurements.size(); i++) {
            xData[i] = i; // Индексы измерений
            yData[i] = measurements.get(i).getValue(); // Температура
        }

        chart.addSeries("Температура", xData, yData);

        // Отображаем график
        new SwingWrapper<>(chart).displayChart();

        // Сохраняем график в PNG
        try {
            BitmapEncoder.saveBitmap(chart, "./TemperatureChart", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
