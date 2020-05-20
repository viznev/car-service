package com.example.demo.controller;

import com.example.demo.exception.CarNotFoundException;
import com.example.demo.exception.ServerException;
import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /*@PostMapping
    public Car add(@RequestBody Car car) {
        return carService.add(car);
    }*/

    public Car addCar(Car car) {
        return carService.addCar(car);
    }

    public List<Car> findCar() {
        try {
            return carService.findCar();
        } catch (CarNotFoundException e) {
            throw new ServerException();
        }
    }

    /*public List<Car> findCar(String vin) {
        carService.findCar(vin);
        return new ArrayList<Car>();
    }*/
}
