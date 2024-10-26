package by.lobanovs.weatherapp.controllers;


import by.lobanovs.weatherapp.dto.MeasurementDTO;
import by.lobanovs.weatherapp.models.Measurement;
import by.lobanovs.weatherapp.models.Sensor;
import by.lobanovs.weatherapp.senderAndClient.MeasurementClient;
import by.lobanovs.weatherapp.senderAndClient.MeasurementSender;
import by.lobanovs.weatherapp.services.MeasurementService;
import by.lobanovs.weatherapp.util.Measurement.MeasurementErrorResponse;
import by.lobanovs.weatherapp.util.Measurement.MeasurementNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementSender measurementSender;
    private final MeasurementClient measurementClient;


    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementSender measurementSender, MeasurementClient measurementClient) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementSender = measurementSender;
        this.measurementClient = measurementClient;
    }

    @PostMapping("/sendMeasurements")
    public String sendMeasurements() {
        measurementSender.sendMeasurements();
        return "Measurements sent!";
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Long> getRainyDaysCount() {
        Long rainyDaysCount = measurementService.getRainyDaysCount();
        return ResponseEntity.ok(rainyDaysCount);
    }

    @GetMapping("/getAllMeasurements")
    public List<MeasurementDTO> fetchAllMeasurements() {
        return measurementClient.getMeasurements();
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.findAll()
                .stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            StringBuilder errors = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                errors.append(fieldError.getField())
                        .append(" : ")
                        .append(fieldError.getDefaultMessage())
                        .append("     ");
            }
            throw new MeasurementNotCreatedException(errors.toString());
        }

        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage()
        );

        // В HTTP ответе тело ответа и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 status
    }


    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);

        // Get the name of the sensor from the DTO
        String sensorName = measurementDTO.getSensor().getName(); // Assuming Sensor has a getName() method

        Sensor sensor = measurementService.findSensorByName(sensorName);

        measurement.setSensor(sensor);
        return measurement;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        MeasurementDTO measurementDTO = modelMapper.map(measurement, MeasurementDTO.class);
        return measurementDTO;
    }

}
