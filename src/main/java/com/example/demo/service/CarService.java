package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car add(Car car) {
        if (car.getVin() == null || car.getModelYear() < 1900) {
            throw new RuntimeException();
        }
        if (carRepository.findById(car.getVin()).isPresent()) {
            throw new RuntimeException();
        }
        return carRepository.save(car);
    }
}
