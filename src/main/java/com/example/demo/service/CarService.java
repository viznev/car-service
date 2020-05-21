package com.example.demo.service;

import com.example.demo.exception.CarNotFoundException;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(Car car) {
        if (car.getVin() == null || car.getModelYear() < 1900) {
            throw new RuntimeException();
        }
        if (carRepository.findById(car.getVin()).isPresent()) {
            throw new RuntimeException();
        }
        return carRepository.save(car);
    }

    public List<Car> findCar() {
        List<Car> results = carRepository.findAll();
        if (results.isEmpty()) {
            throw new CarNotFoundException();
        }
        return results;
    }

    public Car findCar(String vin) {
        return null;
    }
}
