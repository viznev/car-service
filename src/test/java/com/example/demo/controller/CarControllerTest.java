package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarControllerTest {
    private CarController carController;
    private CarService carService;

    @BeforeEach
    public void init() {
        this.carController = new CarController();
    }

    @Test
    public void givenValidCar_whenAddIsCalled_thenDoesNotReturnNull() {
        // Arrange
        Car car = new Car();

        // Act
        Car actual = carController.add(car);

        // Assert
        assertNotNull(actual);
    }
}
