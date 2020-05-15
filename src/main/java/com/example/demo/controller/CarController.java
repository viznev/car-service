package com.example.demo.controller;

import com.example.demo.model.Car;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {
    public Car add(Car car) {
        return car;
    }
}
