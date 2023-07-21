package com.example.carrentalproject.controller;

import com.example.carrentalproject.domain.Car;
import com.example.carrentalproject.dto.CarDto;
import com.example.carrentalproject.mapper.CarMapper;
import com.example.carrentalproject.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
@CrossOrigin("*")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;


    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping("/cars")
    List<CarDto> allCars() {
        log.info("all cars endpoint");

        var cars = carService.getAllCars();

        return cars.stream()
                .map(car -> carMapper.fromEntityToDto(car))
                .toList();
    }

    @PostMapping("/cars")
    ResponseEntity<CarDto> createNewCar(@RequestBody CarDto carToSave, UriComponentsBuilder ucb ) {
        log.info("trying to save new car: [{}]", carToSave);

        Car createdCar = carService.saveCar(carMapper.fromDtoToEntity(carToSave));

        URI path = ucb.path("/api/car/{id}")
                .buildAndExpand(Map.of("id", createdCar.getId()))
                .toUri();

        return ResponseEntity.created(path).body(carMapper.fromEntityToDto(createdCar));



    }
}

