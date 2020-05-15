package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarControllerTest {
    private CarController carController;
    // mock out the dependencies
    @Mock
    private CarService carService;

    @BeforeEach
    public void init() {
        // initialize the mocks
        MockitoAnnotations.initMocks(this);
        this.carController = new CarController(carService);
    }

    @Test
    public void givenValidCar_whenAddIsCalled_thenDoesNotReturnNull() {
        // Arrange
        Car car = new Car();
        when(carService.add(car)).thenReturn(car);

        // Act
        Car actual = carController.add(car);

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void givenValidCar_whenAddIsCalled_thenServiceMethodShouldBeCalledWithCar() {
        // Arrange
        Car car = new Car();

        // Act
        Car actual = carController.add(car);

        // Assert
        verify(carService).add(car);
    }

    @Test
    public void givenValidCar_whenAddIsCalled_thenShouldReturnCarFromService() {
        // Arrange
        Car car = new Car();

        Car expected = new Car();
        when(carService.add(car)).thenReturn(expected);

        // Act
        Car actual = carController.add(car);

        // Assert
        assertEquals(expected, actual);
    }
}
