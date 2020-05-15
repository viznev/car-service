package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CarServiceTest {
    private CarService carService;
    @Mock
    private CarRepository carRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.carService = new CarService(carRepository);
    }

    private Car initializeCar() {
        Car car = new Car();
        car.setVin("vin1");
        car.setModelYear(1900);
        return car;
    }

    /*

    returns not Null & returns a Car
    givenCarWithoutVin thenThrowException
    givenCarModelYearLessThan1900 thenThrowException
    givenCarWithVinAlreadyInRepo thenThrowException

     */

    @Test
    public void givenValidCar_whenAddIsCalled_thenDoesNotReturnNull() {
        // Arrange
        Car car = initializeCar();
        when(carRepository.save(car)).thenReturn(car);

        // Act
        Car actual = carService.add(car);

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void givenCarWithoutVin_whenAddIsCalled_thenThrowException() {
        // Arrange
        Car car = initializeCar();
        car.setVin(null);

        // Act/Assert
        assertThrows(RuntimeException.class, () -> carService.add(car));
    }

    @Test
    public void givenModelYearLessThan1900_whenAddIsCalled_thenThrowException() {
        // Arrange
        Car car = initializeCar();
        car.setModelYear(1899);

        // Act/Assert
        assertThrows(RuntimeException.class, () -> carService.add(car));
    }

    @Test
    public void givenValidCar_whenAddIsCalled_thenReturnCarFromRepository() {
        // Arrange
        Car car = initializeCar();
        Car expected = new Car();
        when(carRepository.save(car)).thenReturn(expected);

        // Act
        Car actual = carService.add(car);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenCarWithVinAlreadyInRepo_whenAddIsCalled_thenThrowException() {
        // Arrange
        Car car = initializeCar();
        when(carRepository.findById(car.getVin())).thenReturn(Optional.of(car));

        // Act/Assert
        assertThrows(RuntimeException.class, () -> carService.add(car));
    }
}
