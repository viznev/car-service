package com.example.demo.controller;

import com.example.demo.exception.CarNotFoundException;
import com.example.demo.exception.ServerException;
import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public Car addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @GetMapping
    public List<Car> findCar() {
        try {
            return carService.findCar();
        } catch (CarNotFoundException e) {
            throw new ServerException();
        }
    }

    @GetMapping("/{vin}")
    public Car findCar(@PathVariable String vin) {
        try {
            return carService.findCar(vin);
        } catch (CarNotFoundException e) {
            throw new ServerException();
        }
    }

    @DeleteMapping("/{vin}")
    public void deleteCar(@PathVariable String vin) {
        if (vin.isEmpty()) {
            throw new ServerException();
        }
        try {
            carService.deleteCar(vin);
        } catch (CarNotFoundException e) {
            throw new ServerException();
        }
    }

    public Car updateCar(Car car) {
        try {
            return carService.updateCar(car);
        } catch (CarNotFoundException e) {
            throw new ServerException();
        }
    }
}
