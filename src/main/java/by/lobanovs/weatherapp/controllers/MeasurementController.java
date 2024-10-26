package by.lobanovs.weatherapp.controllers;


import by.lobanovs.weatherapp.dto.MeasurementDTO;
import by.lobanovs.weatherapp.models.Measurement;
import by.lobanovs.weatherapp.models.Sensor;
import by.lobanovs.weatherapp.services.MeasurementService;
import by.lobanovs.weatherapp.util.Measurement.MeasurementErrorResponse;
import by.lobanovs.weatherapp.util.Measurement.MeasurementNotCreatedException;
import by.lobanovs.weatherapp.util.Sensor.SensorErrorResponse;
import by.lobanovs.weatherapp.util.Sensor.SensorNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
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

}