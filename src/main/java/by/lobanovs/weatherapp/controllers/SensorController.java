package by.lobanovs.weatherapp.controllers;


import by.lobanovs.weatherapp.dto.SensorDTO;
import by.lobanovs.weatherapp.models.Sensor;
import by.lobanovs.weatherapp.services.SensorService;
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
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;


    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult){



        sensorDTO.setName(sensorDTO.getName().trim());

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                errors.append(fieldError.getField())
                        .append(" : ")
                        .append(fieldError
                                .getDefaultMessage())
                        .append("     ");
            }

            throw new SensorNotCreatedException(errors.toString());
        }
        sensorService.save(ConvertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }



    private Sensor ConvertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
    private SensorDTO ConvertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage()
        );

        // В HTTP ответе тело ответа и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 status
    }


}
